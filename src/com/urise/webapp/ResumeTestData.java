package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DataUtil;

import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {

    public static Resume newResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        // contacts
        resume.setContacts(ContactType.PHONE, "+7(921) 855-0482");
        resume.setContacts(ContactType.SKYPE, "grigory.kislin");
        resume.setContacts(ContactType.MAIL, "gkislin@yandex.ru");
        resume.setContacts(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.setContacts(ContactType.GITHUB, "https://github.com/gkislin");
        resume.setContacts(ContactType.STATCKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.setContacts(ContactType.HOME_PAGE, "https://gkislin.ru/");
        //Sections
        resume.setSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.setSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        ArrayList<String> itemsAchievement = new ArrayList<>();
        itemsAchievement.add("С 2013 года: разработка проектов 'Разработка Web приложения','Java Enterprise', " +
                "'Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)'. Организация онлайн стажировок и ведение проектов. " +
                "Более 1000 выпускников.");
        itemsAchievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        resume.setSection(SectionType.ACHIEVEMENT, new ListSection(itemsAchievement));

        ArrayList<String> itemsQualifications = new ArrayList<>();
        itemsQualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        itemsQualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        itemsQualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        resume.setSection(SectionType.QUALIFICATIONS, new ListSection(itemsQualifications));

        ArrayList<Organization> itemsExperience = new ArrayList<>();
        itemsExperience.add(new Organization("Java Online Projects", "https://javaops.ru/",
                DataUtil.of(2013, 10), DataUtil.of(), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
        itemsExperience.add(new Organization("Wrike", "https://www.wrike.com/",
                DataUtil.of(2014, 10), DataUtil.of(2016, 1),
                "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
        resume.setSection(SectionType.EXPERIENCE, new OrganizationSection(itemsExperience));


        ArrayList<Organization> itemsEducation = new ArrayList<>();

        Organization Organization = new Organization("Заочная физико-техническая школа при МФТИ", "https://www.school.mipt.ru/");
        Organization.setPosition(DataUtil.of(1984, 1), DataUtil.of(1987, 6), "Обучение 1", "Описание 1");
        Organization.setPosition(DataUtil.of(1987, 6), DataUtil.of(1990, 1), "Обучение 2", "Описание 2");
        itemsEducation.add(Organization);

        Organization = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/");
        Organization.setPosition(DataUtil.of(1993, 9), DataUtil.NOW, "Аспирантура (программист С, С++)", "");
        Organization.setPosition(DataUtil.of(1987, 9), DataUtil.of(1993, 7), "Инженер (программист Fortran, C)", "");
        itemsEducation.add(Organization);

        resume.setSection(SectionType.EDUCATION, new OrganizationSection(itemsEducation));

        return resume;
    }


    public static void main(String[] args) {

        Resume resume1 = newResume("uuid1", "Георгий");

        System.out.println("Контактная информация:");
        //вывести информацию
        for (ContactType key : resume1.getContacts().keySet()) {
            System.out.println('\t' + key.getTitle() + ": " + resume1.getContacts().get(key));
        }
        System.out.println();

        for (SectionType key : resume1.getSections().keySet()) {
            System.out.println(key.getTitle() + ": ");
            AbstractSection<List> section = resume1.getSections().get(key);

            switch (key) {
                case OBJECTIVE:
                case PERSONAL: {
                    System.out.println('\t' + section.toString());
                    break;
                }
                case EDUCATION:
                case EXPERIENCE: {
                    List<Organization> listOrganization = section.getInfo();
                    for (Organization items : listOrganization) {
                        System.out.println('\t' + items.toString());
                        for (Object position : items.getPosition()) {
                            System.out.println("        " + position.toString());
                        }
                    }
                    break;
                }
                default: {
                    List<String> listSection = section.getInfo();
                    for (Object items : listSection) {
                        System.out.println('\t' + items.toString());
                    }
                }
            }
        }
    }

}