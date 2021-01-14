<%@ page import="javax.persistence.EntityManagerFactory" %>
<%@ page import="javax.persistence.EntityManager" %>
<%@ page import="entities.Publication" %><%--
  Created by IntelliJ IDEA.
  User: nik
  Date: 18.09.2020
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Approval Jsp</title>
</head>
<body>
<%
    if (session.getAttribute("session_id") == null) {
        response.sendRedirect("/login-jsp");
        return;
    }
    EntityManagerFactory factory = (EntityManagerFactory) request.getServletContext().getAttribute("emf");
    EntityManager manager = factory.createEntityManager();
    Publication publication = manager.find(Publication.class, Long.parseLong(request.getParameter("publication")));
%>
<p>
    Public title: <%=publication.getTitle()%>
</p>
<p>
    Public category: <%= publication.getCategory().getCategory()%>
</p>
<p>
    Public user name: <%= publication.getUser().getName()%>(<%=publication.getUser().getLogin()%>)
</p>
<p>
    Public content: <%= publication.getContent()%>
</p>
<p>
    Public date: <%= publication.getPublic_date() %>
</p>
<form action="/approve" method="post" autocomplete="off">
    <input hidden name="publication" value="<%=publication.getId()%>">
    <input type="radio" name="approve" value="true">Approve
    <input type="radio" name="approve" value="false" checked>Refuse
    <p>
        <input class="button" type="submit">
    </p>
</form>
<p>
    <button class="button" onclick="window.location.href = '/start-page-jsp'">Back</button>
</p>
</body>
</html>
