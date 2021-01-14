<%@ page import="entities.Category" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Post</title>
</head>
<body>
<%
    if (session.getAttribute("session_id") == null){
        response.sendRedirect("login-jsp");
    }
%>
<form action="/createpost" method="post" autocomplete="off">
    <p><input class="input" required placeholder="Input post name..." type="text" name="new_post_name"></p>
    <p><textarea required placeholder="Input post text..." name="new_post_text"></textarea><br></p>
    <p>
        <select required name="category">
            <option selected disabled>Select category...</option>
            <%
                List<Category> categories = (List<Category>) request.getAttribute("categories");
                for (Category category : categories) {
            %>
            <option value="
            <%= category.getId()%>
            ">
                <%= category.getCategory()%>
            </option>
            <% } %>
        </select>
    </p>
    <input class="button" type="submit" value="Send">
</form>
<p>
    <button class="button" onclick="window.location.href = '/login-jsp'">Back</button>
</p>

</body>
</html>
