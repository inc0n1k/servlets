<%@ page import="javax.persistence.EntityManagerFactory" %>
<%@ page import="javax.persistence.EntityManager" %>
<%@ page import="java.util.List" %>
<%@ page import="entities.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Ratings And Comments</title>
</head>
<body>
<%
    if (session.getAttribute("session_id") == null) {
        response.sendRedirect("/login-jsp");
        return;
    }
    EntityManagerFactory factory = (EntityManagerFactory) request.getServletContext().getAttribute("emf");
    EntityManager manager = factory.createEntityManager();
    User user = manager.find(User.class, Long.parseLong(session.getAttribute("session_id").toString()));
    Publication publication = manager.find(Publication.class, Long.parseLong(request.getParameter("publication")));
    List<Rating> ratings = manager.createQuery("select r from Rating r where r.publication =?1", Rating.class).
            setParameter(1, publication).
            getResultList();
    double sum = 0;
    for (Rating rating : ratings) {
        sum += rating.getRating();
    }
%>
<p>
    <b>Publication name:</b> <%=publication.getTitle()%>
</p>
<p>
    <b>Publication text:</b> <%=publication.getContent()%>
</p>
<p>
    <b>Publication category:</b> <%=publication.getCategory().getCategory()%>
</p>
<p>
    <b>Average rating: </b><%= (ratings.isEmpty() ? 0 : String.format("%.2f", sum / ratings.size()))%>
</p>
<% if (session.getAttribute("blocked").equals(false)) {%>
<form action="/arac" method="post" autocomplete="off">
    <input hidden name="publication" value="<%= request.getParameter("publication")%>">
    <%
        List<Rating> chek_rating = manager.createQuery("select r from Rating r where r.user = ?1 and r.publication = ?2", Rating.class).
                setParameter(1, user).
                setParameter(2, publication).
                getResultList();
        if (chek_rating.isEmpty()) {
    %>
    <p><b>Rating:</b></p>
    <p>
        <input type="radio" name="rating" value="1" checked>1
        <input type="radio" name="rating" value="2">2
        <input type="radio" name="rating" value="3">3
        <input type="radio" name="rating" value="4">4
        <input type="radio" name="rating" value="5">5
    </p>
    <%}%>
    <p>
        <textarea required name="comment" placeholder="Input post comment..."></textarea>
    </p>
    <input class="button" type="submit" value="Отправить">
</form>
<%}%>
<p>
    <button class="button" onclick="window.location.href = '/start-page-jsp'">Back</button>
</p>
<%
    List<Comment> comments = manager.createQuery("select c from Comment c where c.publication = ?1", Comment.class).
            setParameter(1, publication).
            getResultList();
    if (comments.size() > 0) {
        if (session.getAttribute("error_remove") != null) {
            out.println(session.getAttribute("error_remove"));
            session.removeAttribute("error_remove");
        }
%>
<form action="/rrac" method="post">
    <%
        if (session.getAttribute("blocked").equals(false)) {
            if (!user.getRole().getRole().equals("Пользователь")) {%>
    <input hidden name="publication" value="<%= request.getParameter("publication")%>">
    <input class="button" type="submit" value="Remove">
    <%
            }
        }
    %>
    <p>************************************<br>
        <% for (int i = 0; i < comments.size(); i++) {%>
        <% if (session.getAttribute("blocked").equals(false)) {
            if (!user.getRole().getRole().equals("Пользователь")) {%>
        <input type="checkbox" name="remove" value="<%= comments.get(i).getId() %>">
        <%
                }
            }
        %>
        <b>Comment: </b><%=comments.get(i).getComment()%>
    </p>
    <p>
        <b>User name: </b><%= comments.get(i).getUser().getName()%>
    </p>
    <p>
        <br>************************************
    </p>
    <%
        }//close for
    %>
</form>
<%
    }//close if
%>
</body>
</html>
