import entities.Category;
import entities.Publication;
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
import java.io.PrintWriter;

@WebServlet("/createpost")
public class createPostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        EntityManagerFactory factory = (EntityManagerFactory) getServletContext().getAttribute("emf");
        EntityManager manager = factory.createEntityManager();
        HttpSession session = req.getSession();
        Publication publication = new Publication();
        manager.getTransaction().begin();
        try {
            publication.setCategory(manager.find(Category.class, Long.parseLong(req.getParameter("category").trim())));
            publication.setUser(manager.find(User.class, Long.parseLong(session.getAttribute("session_id").toString().trim())));
            publication.setState(false);
            publication.setTitle(req.getParameter("new_post_name").trim());
            publication.setContent(req.getParameter("new_post_text").trim());
            manager.persist(publication);
            manager.getTransaction().commit();
            resp.sendRedirect("/start-page-jsp");
        } catch (Exception e) {
            e.printStackTrace();
            manager.getTransaction().rollback();
        }
    }//close doPost
}//close class createPostServlet
