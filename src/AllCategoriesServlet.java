import entities.Category;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/allcategories")
public class AllCategoriesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManagerFactory factory = (EntityManagerFactory) getServletContext().getAttribute("emf");
        EntityManager manager = factory.createEntityManager();
        List<Category> categories = manager.
                createQuery("select c from Category c where c.visible = true ", Category.class).getResultList();
        req.setAttribute("categories", categories);
        req.getRequestDispatcher("create-post-jsp").forward(req, resp);
    }//close doPost
}//close class AllCategoriesServlet
