package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            //output contacts
            writeCollection(dos, r.getContacts().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            //output Section
            writeCollection(dos, r.getSections().entrySet(), entry -> {
                SectionType type = entry.getKey();
                AbstractSection section = entry.getValue();
                dos.writeUTF(type.name());
                writeSection(dos, type, section);
            });
        }
    }

    private void writeSection(DataOutputStream dos, SectionType type, AbstractSection section) throws IOException {
        switch (type) {
            case ACHIEVEMENT:
            case QUALIFICATIONS: {
                writeCollection(dos, ((ListSection) section).getInfo(), dos::writeUTF);
                break;
            }
            case EXPERIENCE:
            case EDUCATION: {
                writeCollection(dos, ((OrganizationSection) section).getInfo(), organization -> {
                    dos.writeUTF(organization.getHomePage().getName());
                    dos.writeUTF(organization.getHomePage().getUrl());
                    writeCollection(dos, organization.getPosition(), position -> {
                        dos.writeUTF(position.getStartDate().toString());
                        dos.writeUTF(position.getEndDate().toString());
                        dos.writeUTF(position.getTitle());
                        dos.writeUTF(position.getDescription());
                    });
                });
                break;
            }
            default: {
                dos.writeUTF(section.toString());
            }
        }
    }

    @FunctionalInterface
    private interface Converter<T> {
        void convert(T t) throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, Converter<T> items) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            items.convert(item);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            //input contacts
            readItems(dis, () -> resume.setContacts(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            //input Section
            readItems(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.setSection(sectionType, readSection(dis, sectionType));
            });
            return resume;
        }
    }

    @FunctionalInterface
    private interface Processor {
        void read() throws IOException;
    }

    private void readItems(DataInputStream dis, Processor processor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            processor.read();
        }
    }

    private AbstractSection readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readList(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection(
                        readList(dis, () -> new Organization(dis.readUTF(), dis.readUTF(),
                                readList(dis, () -> new Organization.Position(
                                        readLocalDate(dis),
                                        readLocalDate(dis),
                                        dis.readUTF(),
                                        dis.readUTF()
                                ))
                        )));
            default:
                return new TextSection(dis.readUTF());
        }
    }

    @FunctionalInterface
    private interface ListReader<T> {
        T read() throws IOException;
    }

    private <T> List<T> readList(DataInputStream dis, ListReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.parse(dis.readUTF());
    }
}
