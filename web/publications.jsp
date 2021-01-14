<%@ page import="java.util.List" %>
<%@ page import="entities.Publication" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Publications</title>
</head>
<body>
<%
    if (session.getAttribute("session_id") == null) {
        response.sendRedirect("/login-jsp");
        return;
    }
    List<Publication> publications = (List<Publication>) request.getAttribute("publications");
    for (Publication publication : publications) {
%>
<p>
    <a href="/arac-jsp?publication=<%=publication.getId()%>">
        <%= publication.getTitle()%>
    </a>
</p>
<% }%>
<p>
    <button class="button" onclick="window.location.href = '/start-page-jsp'">Back</button>
</p>
</body>
</html>
