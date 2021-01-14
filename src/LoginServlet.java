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
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req /*запрос*/, HttpServletResponse resp /*ответ*/) throws IOException {
        EntityManagerFactory factory = (EntityManagerFactory) getServletContext().getAttribute("emf");
        EntityManager manager = factory.createEntityManager();
        HttpSession session = req.getSession();
        List<User> result = manager.
                createQuery("select u from  User u where u.login=?1 and  u.password = ?2", User.class).
                setParameter(1, req.getParameter("login")).
                setParameter(2, Base64.getEncoder().encodeToString(req.getParameter("pass").getBytes())).
                getResultList();
        if (result.size() != 0) {
//            if (result.get(0).getBlocked()) {
//                session.setAttribute("login_info", "The user is blocked, contact the administrator ...");
//                resp.sendRedirect("/login-jsp");
//            } else {
            session.setAttribute("session_id", result.get(0).getId());
            session.setAttribute("role_id", result.get(0).getRole().getId());
            session.setAttribute("blocked", result.get(0).getBlocked());
            resp.sendRedirect("/start-page-jsp");
//            }
        } else {
            session.setAttribute("login_info", "Увы, введены неправильные данные...");
            resp.sendRedirect("/login-jsp");
        }
    }//close doPost

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/login-jsp");
    }
}//close class LoginServlet
