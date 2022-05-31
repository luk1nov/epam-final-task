package by.lukyanov.finaltask.controller;

import java.io.IOException;
import by.lukyanov.finaltask.command.*;
import by.lukyanov.finaltask.exception.CommandException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.lukyanov.finaltask.command.ParameterAttributeName.COMMAND;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

@WebServlet(name = "controller", urlPatterns = "/controller")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5
        , fileSizeThreshold = 1024 * 1024 * 5
        , maxRequestSize = 1024 * 1024 * 5 * 5)
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String commandParameter = request.getParameter(COMMAND);
            Command command = CommandType.of(commandParameter);
            logger.info(commandParameter);
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
            response.sendError(SC_INTERNAL_SERVER_ERROR, e.getCause().getMessage());
        }
    }
}