<%@ page import="javax.persistence.EntityManagerFactory" %>
<%@ page import="javax.persistence.EntityManager" %>
<%@ page import="entities.User" %>
<%@ page import="java.util.List" %>
<%@ page import="entities.Role" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Profile</title>
</head>
<body>
<%
    if (session.getAttribute("session_id") == null) {
        response.sendRedirect("/login-jsp");
    } else {
        EntityManagerFactory factory = (EntityManagerFactory) request.getServletContext().getAttribute("emf");
        EntityManager manager = factory.createEntityManager();
//        User user = manager.find(User.class, Long.parseLong(session.getAttribute("session_id").toString()));
        User edit_user = manager.find(User.class, Long.parseLong(request.getParameter("edit_user")));
%>
<form action="/editprofile" method="post" autocomplete="off">
    <input hidden name="id" value="<%= edit_user.getId() %>">
    <p>
        User name:<br>
        <input value=<%=edit_user.getName()%> type="text" name="name">
    </p>
    <p>
        User surname:<br>
        <input value="<%= edit_user.getSurname()%>" type="text" name="surname">
    </p>
    <%
        List<Role> roles = manager.createQuery("select r from Role r", Role.class).getResultList();
    %>
    <p>
        User role:
        <select name="role">
            <%
                for (Role role : roles) {
            %>
            <option
                    <%
                        if (role.getRole().equals(edit_user.getRole().getRole())) {
                    %>
                    selected
                    <%
                        }
                    %>
                    value="<%= role.getId() %>">
                <%= role.getRole() %>
            </option>
            <%}%>
        </select>
    </p>
    <p>Блокировка:
        <input type="radio" name="blocked" value="true" <%= edit_user.getBlocked()?"checked":"" %>>Yes
        <input type="radio" name="blocked" value="false" <%= edit_user.getBlocked()?"":"checked" %>>No
    </p>
    <p>
        Введите пароль для сохранения изменений:
    </p>
    <p>
        <%
            if (request.getAttribute("error") != null) {
                out.print(request.getAttribute("error"));
            }
        %>
    </p>
    <input placeholder="Password..." type="password" name="pass">
    <input type="submit">
</form>
<%
    }
%>
<p>
    <button class="button" onclick="window.location.href = '/start-page-jsp'">Back</button>
</p>

</body>
</html>
