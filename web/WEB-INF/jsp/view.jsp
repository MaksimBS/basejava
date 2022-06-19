<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.Organization" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../../css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>

<body>

<jsp:include page="fragments/header.jsp"/>

<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>

        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
        <c:choose>

        <c:when test="${type=='PERSONAL' || type=='OBJECTIVE'}">
                <%
            String[] str = ((TextSection) section).getContent().split("\n");
            request.setAttribute("textSection", str);
            request.setAttribute("size", str.length);
        %>
        <c:if test="${size>0 && !textSection[0].equals('')}">
    <h3><%=sectionEntry.getKey().getTitle()%>
    </h3>
    <c:forEach var="text" items="${textSection}">
        ${text}<br>
    </c:forEach>
    </c:if>
    </c:when>

    <c:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
        <%
            request.setAttribute("listSection", ((ListSection) section).getListSection());
            request.setAttribute("listSize", ((ListSection) section).getListSection().size());
        %>
        <c:if test="${listSize>0 && !listSection.get(0).equals('')}">
            <h3><%=sectionEntry.getKey().getTitle()%>
            </h3>
            <c:forEach var="text" items="${listSection}">
                <li type="disc">${text}</li>
            </c:forEach>
        </c:if>
    </c:when>

    <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
        <%
            List<Organization> organizationList = ((OrganizationSection) sectionEntry.getValue()).getInfo();
            request.setAttribute("orgList", organizationList);
        %>
        <c:forEach var="org" items="${orgList}">
            <h4><a href="${org.homePage}">${org.position}</a></h4>
            <c:forEach var="period" items="${org.position}">
                <p>${period.startDate} - ${period.endDate}</p>
                <strong>${period.title}</strong>
                <p>${period.description}</p>
            </c:forEach>
        </c:forEach>

    </c:when>

    </c:choose>
    <br>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>