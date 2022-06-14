package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ResumeServlet extends javax.servlet.http.HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        boolean isAdd = (uuid.length()==0);
        Resume r;
        if (isAdd) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.setContacts(type, deleteCRLFOnce(value));
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (value != null && values.length != 0) {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        r.setSection(type, new TextSection(deleteCRLFOnce(value)));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection listSection = new ListSection(listDeleteCRLFOnce(value));
                        r.setSection(type, listSection);
                }
            } else {
                r.getSections().remove(type);
            }
        }

        if (isAdd){
            if (fullName.length()!=0){
                storage.save(r);}
        }
        else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    private List<String> listDeleteCRLFOnce(String value) {
        String[] array = value.split("\\n");
        List<String> list = new LinkedList<>();
        for (String str : array) {
            if (!str.equals("\r")) {
                list.add(deleteCRLFOnce(str));
            }
        }
        return list;
    }

    private static String deleteCRLFOnce(String input) {
        input = input.replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1");
        return input.trim();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            case "add":
                Resume empty = new Resume("","");
                for (SectionType type : SectionType.values()) {
                    switch (type) {
                        case PERSONAL:
                        case OBJECTIVE:
                            empty.setSection(type, new TextSection(""));
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            ListSection section = new ListSection();
                            section.addToListSection("");
                            empty.setSection(type, section);
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            break;
                    }
                }
                r = empty;
                break;
            default:
                throw new IllegalArgumentException("Action " + action + "is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                .forward(request, response);
    }
}
