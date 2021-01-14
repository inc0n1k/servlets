<%@ page import="java.util.List" %>
<%@ page import="entities.User" %>
<%@ page import="javax.persistence.EntityManagerFactory" %>
<%@ page import="javax.persistence.EntityManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Profiles</title>
</head>
<body>
<%
    if (session.getAttribute("session_id") == null) {
        response.sendRedirect("login-jsp");
    } else {
        EntityManagerFactory factory = (EntityManagerFactory) request.getServletContext().getAttribute("emf");
        EntityManager manager = factory.createEntityManager();
        User user = manager.find(User.class, Long.parseLong(session.getAttribute("session_id").toString()));
        List<User> users = manager.createQuery("select u from User u order by u.surname", User.class).getResultList();
        switch (user.getRole().getRole()) {
            case "Администратор":
                for (User for_user : users) {
%>
<p>
    <a href="/edit-profile-jsp?edit_user=<%=for_user.getId()%>">
        <%= for_user.getSurname() %>
        <%= for_user.getName() %>
        (<%= for_user.getLogin() %>)
    </a>
</p>
<%
        }
        break;
    case "Модератор":
        for (User for_user : users) {
            if (!for_user.getRole().getRole().equals("Администратор")) {
%>
<p>
    <a href="/edit-profile-jsp?edit_user=<%=for_user.getId()%>">
        <%= for_user.getSurname() %>
        <%= for_user.getName() %>
        (<%= for_user.getLogin() %>)
    </a>
</p>
<%
                    }
                }
                break;
            case "Пользователь":
                response.sendRedirect("/start-page-jsp");
                break;
        }
    }
%>
<p>
    <button class="button" onclick="window.location.href = '/start-page-jsp'">Back</button>
</p>
</body>
</html>
