import entities.Role;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req /*запрос*/, HttpServletResponse resp /*ответ*/) throws IOException {
        EntityManagerFactory factory = (EntityManagerFactory) req.getServletContext().getAttribute("emf");
        EntityManager manager = factory.createEntityManager();
        String login = req.getParameter("login");
        List<User> users = manager.createQuery("select u from User u where u.login = ?1", User.class).
                setParameter(1, login).
                getResultList();
        HttpSession session = req.getSession();
//        System.out.println(users.size());
//        System.out.println(users.get(0).getLogin());
//        System.out.println();
        if (users.size() == 0) {
            if (login.matches("[A-Za-z][A-Za-z_\\d]{4,}")) {
                User new_user = new User();
                Role role = manager.find(Role.class, 3L);
                try {
                    manager.getTransaction().begin();
                    new_user.setLogin(login);
                    new_user.setPassword(Base64.getEncoder().encodeToString(req.getParameter("pass").getBytes()));
                    new_user.setName(req.getParameter("name"));
                    new_user.setSurname(req.getParameter("surname"));
                    new_user.setRole(role);
//                    new_user.setBlocked(false);
                    manager.persist(new_user);
                    resp.sendRedirect("/login-jsp");
                    manager.getTransaction().commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    manager.getTransaction().rollback();
                }
            } else {
                session.setAttribute("reg_info", "Длина логина меньше 5 символов либо логин начинается со знака _ или с цифры");
                resp.sendRedirect("/registration-jsp");
            }
        } else {
            session.setAttribute("reg_info", "Login exists...");
            resp.sendRedirect("/registration-jsp");
        }
    } //close doPost
} //close class RegistrationServlet