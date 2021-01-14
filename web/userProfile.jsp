<%@ page import="entities.User" %>
<%@ page import="javax.persistence.EntityManagerFactory" %>
<%@ page import="javax.persistence.EntityManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
</head>
<body>
<%
    if (session.getAttribute("session_id") == null) {
        response.sendRedirect("/login-jsp");
    } else {
        EntityManagerFactory factory = (EntityManagerFactory) request.getServletContext().getAttribute("emf");
        EntityManager manager = factory.createEntityManager();
        User user = manager.find(User.class, Long.parseLong(session.getAttribute("session_id").toString()));
%>
<p>
    <b>User name: </b> <%= user.getName() %>
</p>
<p>
    <b>User surname: </b> <%= user.getSurname() %>
</p>
<p>
    <b>User login: </b> <%= user.getLogin() %>
</p>
<p>
    <b>User registration date: </b> <%= user.getRegistration_date() %>
</p>
<p>
    <b>User role: </b> <%= user.getRole().getRole() %>
</p>
<%
    }
%>
<p>
    <button class="button" onclick="window.location.href = '/start-page-jsp'">Back</button>
</p>

</body>
</html>
