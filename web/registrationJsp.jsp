<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration Page</title>
</head>
<body>
<link rel="stylesheet" href="css/registration.css">
<%
    if (session.getAttribute("session_id") != null) {
        response.sendRedirect("/start-page-jsp");
    }
%>
<% if (session.getAttribute("reg_error") != null) { %>
<p>
    <%= session.getAttribute("reg_error")%>
</p>
<%
    }
    session.removeAttribute("reg_error");
%>
<form action="/registration" method="post" autocomplete="off"><br>
    <input class="input" required placeholder="Input login..." type="text" name="login"><br>
    <input class="input" required placeholder="Input password..." type="password" name="pass"><br>
    <input class="input" required placeholder="Input name..." type="text" name="name"><br>
    <input class="input" required placeholder="Input surname..." type="text" name="surname"><br>
    <input class="button" type="submit" value="Registration">
    <button class="button" onclick="window.location.href = '/login-jsp'">Back</button>
</form>
</body>
</html>
