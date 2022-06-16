package by.lukyanov.finaltask.command.impl;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.lukyanov.finaltask.command.PagePath.MAIN_PAGE;
import static by.lukyanov.finaltask.command.ParameterAttributeName.CURRENT_PAGE;

public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        HttpSession session = request.getSession();
        session.setAttribute(CURRENT_PAGE, MAIN_PAGE);
        router.setPagePath(MAIN_PAGE);
        return router;
    }
}
