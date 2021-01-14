package listeners;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class EntityManagerFactoryListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("project");
        sce.getServletContext().setAttribute("emf", factory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        EntityManagerFactory factory = (EntityManagerFactory) sce.getServletContext().getAttribute("emf");
        factory.close();
    }
}
