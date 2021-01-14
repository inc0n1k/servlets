import entities.Comment;
import entities.Publication;
import entities.Rating;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/arac")
public class AddRatingsAndCommentsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManagerFactory factory = (EntityManagerFactory) getServletContext().getAttribute("emf");
        EntityManager manager = factory.createEntityManager();
        HttpSession session = req.getSession();

        Comment comment = new Comment();
        User user = manager.find(User.class, Long.parseLong(session.getAttribute("session_id").toString()));
        Publication publication = manager.find(Publication.class, Long.parseLong(req.getParameter("publication")));
        manager.getTransaction().begin();
        try {
            if (req.getParameter("rating") != null) {
                Rating rating = new Rating();
                rating.setRating(Integer.parseInt(req.getParameter("rating")));
                rating.setPublication(publication);
                rating.setUser(user);
                manager.persist(rating);
            }
            comment.setUser(user);
            comment.setPublication(publication);
            comment.setComment(req.getParameter("comment"));
            manager.persist(comment);
            resp.sendRedirect("/arac-jsp?publication=" + publication.getId());
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
        }
    }//close doPost
}//close class AddRatingsAndCommentsServlet
