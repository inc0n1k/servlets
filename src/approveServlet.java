import entities.Publication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/approve")
public class approveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManagerFactory factory = (EntityManagerFactory) getServletContext().getAttribute("emf");
        EntityManager manager = factory.createEntityManager();
        Publication publication = manager.find(Publication.class, Long.parseLong(req.getParameter("publication")));
        boolean approve = Boolean.parseBoolean(req.getParameter("approve"));
        try {
            manager.getTransaction().begin();
            if (approve) {
                publication.setState(approve);
                resp.sendRedirect("/for-approval-jsp");
                manager.getTransaction().commit();
            } else {
                manager.remove(publication);
                resp.sendRedirect("/for-approval-jsp");
                manager.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            manager.getTransaction().rollback();
        }
    }
}
