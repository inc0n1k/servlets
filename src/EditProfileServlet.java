import entities.Role;
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
import java.util.Base64;

@WebServlet("/editprofile")
public class EditProfileServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("session_id") == null) {
            resp.sendRedirect("/login-jsp");
            return;
        }
        EntityManagerFactory factory = (EntityManagerFactory) getServletContext().getAttribute("emf");
        EntityManager manager = factory.createEntityManager();
        User user = manager.find(User.class, Long.parseLong(session.getAttribute("session_id").toString()));
        User edit_user = manager.find(User.class, Long.parseLong(req.getParameter("id")));
        if (user.getPassword().equals(Base64.getEncoder().encodeToString(req.getParameter("pass").getBytes()))) {
            manager.getTransaction().begin();
            try {
                edit_user.setName(req.getParameter("name"));
                edit_user.setSurname(req.getParameter("surname"));
                edit_user.setRole(manager.find(Role.class, Long.parseLong(req.getParameter("role"))));
                edit_user.setBlocked(Boolean.parseBoolean(req.getParameter("blocked")));
                manager.getTransaction().commit();
                session.setAttribute("user", user);
            } catch (Exception e) {
                manager.getTransaction().rollback();
            }
            resp.sendRedirect("/edit-profile-jsp?edit_user=" + edit_user.getId());
        } else {
            req.setAttribute("error", "Неправильный пароль...");
            req.getRequestDispatcher("/edit-profile-jsp?edit_user=" + edit_user.getId()).forward(req, resp);
        }
    }//close doPost
}//close class Edit