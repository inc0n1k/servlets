<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Start Page</title>
</head>
<body>
<%
    if (session.getAttribute("session_id") == null) {
        response.sendRedirect("login-jsp");
    } else {
%>
<p>
    <a href="userprofile">User Profile</a>
</p>
<% if (session.getAttribute("blocked").equals(false)) {%>
<p>
    <a href="allcategories">Create Post</a>
</p>
<%}%>
<p>
    <a href="publications">All Publications</a>
</p>
<p>
    <a href="publications">Top 10</a>
</p>
<% if (((Long) session.getAttribute("role_id")) != 3L) {
    if (session.getAttribute("blocked").equals(false)) {
%>
<p>
    <a href="all-profiles-jsp">All profiles</a>
</p>
<p>
    <a href="for-approval-jsp">For approval</a>
</p>
<%
            }
        }
    }
%>
<p>
    <button class="button" onclick="window.location.href = '/exit'">Exit</button>
</p>
</body>
</html>
