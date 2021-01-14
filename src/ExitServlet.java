import entities.User;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/exit")
public class ExitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("session_id");
        session.removeAttribute("role_id");
        session.removeAttribute("blocked");
//        resp.sendRedirect("/login-jsp");
        EntityManagerFactory factory = (EntityManagerFactory) getServletContext().getAttribute("emf");
        EntityManager manager = factory.createEntityManager();
        List<User> users = manager.createQuery("select u from User u", User.class).getResultList();
        PrintWriter pw = resp.getWriter();
        for (User user : users) {
            pw.println(user.getId());
            pw.println(user.getLogin());
            pw.println(user.getName());
        }
        pw.close();

    }
}
