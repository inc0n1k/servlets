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

@WebServlet("/rrac")
public class RemoveRatingsAndCommentsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("session_id") == null) {
            resp.sendRedirect("/login-jsp");
            return;
        }
        if (req.getParameterValues("remove") == null) {
            resp.sendRedirect("/arac-jsp?publication=" + req.getParameter("publication"));
            session.setAttribute("error_remove", "No comments were selected...");
            return;
        }
        EntityManagerFactory factory = (EntityManagerFactory) getServletContext().getAttribute("emf");
        EntityManager manager = factory.createEntityManager();
        User user = manager.find(User.class, Long.parseLong(session.getAttribute("session_id").toString()));
        if (!user.getRole().getRole().equals("Пользователь")) {
            String[] for_remove = req.getParameterValues("remove");
            try {
                Comment comment;
                manager.getTransaction().begin();
                for (String remove_com : for_remove) {
                    comment = manager.createQuery("select c from Comment  c where c.id  = ?1", Comment.class).
                            setParameter(1, Long.parseLong(remove_com)).
                            getSingleResult();
                    manager.remove(comment);
                }
                resp.sendRedirect("/arac-jsp?publication=" + req.getParameter("publication"));
                manager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
                manager.getTransaction().rollback();
            }
        }
    }//close doPost
}//close class RemoveRatingsAndCommentsServlet
