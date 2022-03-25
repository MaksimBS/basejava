package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DataUtil;

import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume1 = new Resume("Григорий Кислин");
        //Contacts
        resume1.contacts.put(ContactType.PHONE, "+7(921) 855-0482");
        resume1.contacts.put(ContactType.SKYPE, "grigory.kislin");
        resume1.contacts.put(ContactType.MAIL, "gkislin@yandex.ru");
        resume1.contacts.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume1.contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        resume1.contacts.put(ContactType.STATCKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume1.contacts.put(ContactType.HOME_PAGE, "https://gkislin.ru/");
        //Sections
        resume1.sections.put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume1.sections.put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        ArrayList<String> itemsAchievement = new ArrayList<>();
        itemsAchievement.add("С 2013 года: разработка проектов 'Разработка Web приложения','Java Enterprise', " +
                "'Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)'. Организация онлайн стажировок и ведение проектов. " +
                "Более 1000 выпускников.");
        itemsAchievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        resume1.sections.put(SectionType.ACHIEVEMENT, new ListSection(itemsAchievement));

        ArrayList<String> itemsQualifications = new ArrayList<>();
        itemsQualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        itemsQualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        itemsQualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        resume1.sections.put(SectionType.QUALIFICATIONS, new ListSection(itemsQualifications));

        ArrayList<Organization> itemsExperience = new ArrayList<>();
        itemsExperience.add(new Organization("Java Online Projects", "https://javaops.ru/",
                DataUtil.of(2013,10), DataUtil.of(), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
        itemsExperience.add(new Organization("Wrike", "https://www.wrike.com/",
                DataUtil.of(2014, 10), DataUtil.of(2016, 1),
                "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
        resume1.sections.put(SectionType.EXPERIENCE,new OrganizationSection(itemsExperience));

        ArrayList<Organization> itemsEducation = new ArrayList<>();
        itemsEducation.add(new Organization("Заочная физико-техническая школа при МФТИ", "https://www.school.mipt.ru/",
                DataUtil.of(1984, 1), DataUtil.of(1987, 6), "Закончил с отличием", ""));
        itemsEducation.add(new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "https://www.ifmo.ru/",
                DataUtil.of(1987, 9), DataUtil.of(1993, 7),
                "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));

        resume1.sections.put(SectionType.EDUCATION,new OrganizationSection(itemsEducation));


        System.out.println("Контактная информация:");
        //вывести информацию
        for (ContactType key : resume1.contacts.keySet()) {
            System.out.println(key.getTitle() + ": " +  resume1.contacts.get(key));
        }
        System.out.println();

        for (SectionType key : resume1.sections.keySet()) {
            System.out.println(key.getTitle() + ": ");
            Section section = resume1.sections.get(key);

            switch (key) {
                case OBJECTIVE, PERSONAL -> System.out.println(section.toString());
                default -> {
                    List<Object> listSection = (List) section.getInfo();
                    for (Object items : listSection) {
                        System.out.println(items.toString());
                    }
                }
            }
        }
    }
}
