import entities.Publication;
import entities.Rating;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/top10public")
public class Top10Publications extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManagerFactory factory = (EntityManagerFactory) getServletContext().getAttribute("emf");
        EntityManager manager = factory.createEntityManager();
        PrintWriter pw = resp.getWriter();
        List<Publication> publications = manager.createQuery("select p from Publication p left " +
                        "join Rating r on p = r.publication group by p.id order by avg(r.rating) desc",
                Publication.class).
                setMaxResults(10).
                getResultList();
        for (Publication publication : publications) {
            double sum = 0;
            List<Rating> ratings = manager.createQuery("select r from Rating r where r.publication =?1", Rating.class).
                    setParameter(1, publication).
                    getResultList();
            for (Rating rating : ratings) {
                sum += rating.getRating();
            }
            pw.print("Post name: ");
            pw.println(publication.getTitle());
            pw.print("Average rating: ");
            pw.println(sum / ratings.size());
            pw.println("----");
        }
        pw.close();
    }
}
