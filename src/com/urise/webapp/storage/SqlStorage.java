package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));

    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume  SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                String uuid = resume.getUuid();
                ps.setString(2, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            deleteContacts(conn, resume);
            insertContact(conn, resume);
            return null;
        });
    }

    private void deleteContacts(Connection conn, Resume resume) {
        sqlHelper.execute("DELETE  FROM contact WHERE resume_uuid=?", ps -> {
            ps.setString(1, resume.getUuid());
            ps.execute();
            return null;
        });
    }

    private void insertContact(Connection conn, Resume resume) throws SQLException {
        if (resume != null) {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, e.getKey().name());
                    ps.setString(3, e.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES(?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContact(conn, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "SELECT * FROM resume r " +
                        "LEFT JOIN contact c " +
                        "ON r.uuid = c.resume_uuid " +
                        "WHERE r.uuid = ? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        String value = rs.getString("value");
                        if (value != null) {
                            ContactType type = ContactType.valueOf(rs.getString("type"));
                            resume.setContacts(type, value);
                        }
                    } while (rs.next());
                    return resume;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            if (!ps.execute()) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> list = new HashMap<>();
        return sqlHelper.execute("" +
                "SELECT * FROM resume r " +
                "LEFT JOIN contact c " +
                "ON r.uuid = c.resume_uuid" +
                " ORDER BY full_name,uuid ", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                Resume resume = list.get(uuid);
                if (resume == null) {
                    resume = new Resume(uuid, rs.getString("full_name"));
                    list.put(uuid, resume);
                }
                setContacts(rs, resume);
            }
            List<Resume> resultList = new ArrayList<>(list.values());
            Collections.sort(resultList);
            return resultList;
        });
    }

    private void setContacts(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            r.setContacts(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }
}
