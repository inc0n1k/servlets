<%@ page import="javax.persistence.EntityManagerFactory" %>
<%@ page import="javax.persistence.EntityManager" %>
<%@ page import="java.util.List" %>
<%@ page import="entities.Publication" %>
<%@ page import="com.mysql.cj.util.DnsSrv" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>For Approval</title>
</head>
<body>
<%
    if (session.getAttribute("session_id") == null) {
        response.sendRedirect("/login-jsp");
        return;
    }
    EntityManagerFactory factory = (EntityManagerFactory) request.getServletContext().getAttribute("emf");
    EntityManager manager = factory.createEntityManager();
    List<Publication> publications = manager.createQuery("select p from Publication p where p.state = false", Publication.class).
            getResultList();
%>
<form>
    <%
        for (Publication publication : publications) {
    %>
    <p>
        <a href="/approval-jsp?publication=<%=publication.getId()%>" name="publication">
            <%= publication.getTitle()%>
        </a>
    </p>
    <%}%>
</form>
<p>
    <button class="button" onclick="window.location.href = '/start-page-jsp'">Back</button>
</p>
</body>
</html>
