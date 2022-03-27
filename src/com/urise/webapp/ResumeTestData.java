package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DataUtil;

import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {

    public static Resume newResume (String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        // contacts
        resume.contacts.put(ContactType.PHONE, "+7(921) 855-0482");
        resume.contacts.put(ContactType.SKYPE, "grigory.kislin");
        resume.contacts.put(ContactType.MAIL, "gkislin@yandex.ru");
        resume.contacts.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        resume.contacts.put(ContactType.STATCKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.contacts.put(ContactType.HOME_PAGE, "https://gkislin.ru/");
        //Sections
        resume.sections.put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.sections.put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        ArrayList<String> itemsAchievement = new ArrayList<>();
        itemsAchievement.add("С 2013 года: разработка проектов 'Разработка Web приложения','Java Enterprise', " +
                "'Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)'. Организация онлайн стажировок и ведение проектов. " +
                "Более 1000 выпускников.");
        itemsAchievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        resume.sections.put(SectionType.ACHIEVEMENT, new ListSection(itemsAchievement));

        ArrayList<String> itemsQualifications = new ArrayList<>();
        itemsQualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        itemsQualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        itemsQualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        resume.sections.put(SectionType.QUALIFICATIONS, new ListSection(itemsQualifications));

        ArrayList<Organization> itemsExperience = new ArrayList<>();
        itemsExperience.add(new Organization("Java Online Projects", "https://javaops.ru/",
                DataUtil.of(2013,10), DataUtil.of(), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
        itemsExperience.add(new Organization("Wrike", "https://www.wrike.com/",
                DataUtil.of(2014, 10), DataUtil.of(2016, 1),
                "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
        resume.sections.put(SectionType.EXPERIENCE,new OrganizationSection(itemsExperience));


        ArrayList<Organization> itemsEducation = new ArrayList<>();

        Organization Organization = new Organization("Заочная физико-техническая школа при МФТИ", "https://www.school.mipt.ru/");
        Organization.setPosition(DataUtil.of(1984, 1), DataUtil.of(1987, 6), "Обучение 1", "Описание 1");
        Organization.setPosition(DataUtil.of(1987, 6), DataUtil.of(1990, 1), "Обучение 2", "Описание 2");
        itemsEducation.add(Organization);

        Organization = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики","http://www.ifmo.ru/");
        Organization.setPosition(DataUtil.of(1993, 9), DataUtil.of(1996, 7), "Аспирантура (программист С, С++)", "");
        Organization.setPosition(DataUtil.of(1987, 9), DataUtil.of(1993, 7), "Инженер (программист Fortran, C)", "");
        itemsEducation.add(Organization);

        resume.sections.put(SectionType.EDUCATION,new OrganizationSection(itemsEducation));

        return resume;
    }

    public static void main(String[] args) {

        Resume resume1 = newResume("uuid1", "Георгий");

        System.out.println("Контактная информация:");
        //вывести информацию
        for (ContactType key : resume1.contacts.keySet()) {
            System.out.println('\t'+ key.getTitle() + ": " +  resume1.contacts.get(key));
        }
        System.out.println();

        for (SectionType key : resume1.sections.keySet()) {
            System.out.println(key.getTitle() + ": ");
            Section section = resume1.sections.get(key);

            switch (key) {
                case OBJECTIVE, PERSONAL -> System.out.println('\t' + section.toString());
                case EDUCATION, EXPERIENCE -> {
                    List<Organization> listSection = (List) section.getInfo();
                    for (Organization items : listSection) {
                        System.out.println('\t' + items.toString());
                        for (Object position : items.getPosition()) {
                            System.out.println("        " + position.toString());
                        }
                    }
                }
                default -> {
                    List<Object> listSection = (List) section.getInfo();
                    for (Object items : listSection) {
                        System.out.println('\t' + items.toString());
                    }
                }
            }
        }
    }
}
