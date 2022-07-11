package by.lukyanov.finaltask.controller.listener;

import by.lukyanov.finaltask.model.connection.ConnectionPool;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;

/**
 * Servlet context listener.
 */
@WebListener
public class ServletContextListenerImpl implements ServletContextListener {

    /**
     * Calls to connection pool for db connection setup at application launch.
     * @see ConnectionPool
     *
     * @param sce ServletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.getInstance();
    }

    /**
     * Destroys connections during context destroying.
     * @see ConnectionPool
     *
     * @param sce ServletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyPool();
    }
}
