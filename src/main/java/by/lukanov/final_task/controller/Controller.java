package by.lukanov.final_task.controller;

import java.io.*;

import by.lukanov.final_task.command.*;
import by.lukanov.final_task.model.connection.ConnectionPool;
import by.lukanov.final_task.exception.CommandException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

@WebServlet(name = "controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void init() throws ServletException {
        logger.info("Servlet initialized " + this.getServletName());
        ConnectionPool.getInstance();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void destroy() {
        logger.info("Servlet destroyed " + this.getServletName());
        ConnectionPool.getInstance().destroyPool();
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try {
            Command command = CommandProvider.defineCommand(request);
            Router router = command.execute(request);

            switch (router.getType()) {
                case FORWARD -> {
                    RequestDispatcher dispatcher = request.getRequestDispatcher(router.getPagePath());
                    dispatcher.forward(request, response);
                }
                case REDIRECT -> {
                    response.sendRedirect(router.getPagePath());
                }
                default -> {
                    response.sendRedirect(PagePath.MAIN_PAGE);
                }
            }
        } catch (CommandException e){
            response.sendError(SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}