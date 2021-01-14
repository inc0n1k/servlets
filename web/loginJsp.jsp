<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
<%
    if (session.getAttribute("session_id") != null)
        response.sendRedirect("start-page-jsp");
%>
<% if (session.getAttribute("login_info") != null) {%>
<p>
    <%= session.getAttribute("login_info")%>
</p>
<% session.removeAttribute("login_info"); %>
<%
    }
%>

<form action="login" method="post" autocomplete="off"><br>
    <input required placeholder="Input login..." type="text" name="login"><br>
    <input required placeholder="Input password..." type="password" name="pass"><br>
    <input class="button" type="submit" value="Login">
    <button class="button" onclick="window.location.href = 'registration-jsp'">Registration</button>
</form>
</body>
</html>