package by.lukyanov.finaltask.controller.listener;

import by.lukyanov.finaltask.model.connection.ConnectionPool;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyPool();
    }
}
