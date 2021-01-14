import entities.Publication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/publications")
public class PublicationsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManagerFactory factory = (EntityManagerFactory) getServletContext().getAttribute("emf");
        EntityManager manager = factory.createEntityManager();
        List<Publication> publications = manager.
                createQuery("select p from Publication p where p.state = true ", Publication.class).
                getResultList();
        req.setAttribute("publications", publications);
        req.getRequestDispatcher("/publications-jsp").forward(req, resp);
    }//close doGet
}//close class PublicationsServlet
