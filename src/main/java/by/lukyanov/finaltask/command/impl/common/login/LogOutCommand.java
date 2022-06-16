package by.lukyanov.finaltask.command.impl.common.login;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.ParameterAttributeName;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.lukyanov.finaltask.command.ParameterAttributeName.MESSAGE;

public class LogOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        request.setAttribute(MESSAGE, request.getParameter(MESSAGE));
        session.removeAttribute(ParameterAttributeName.LOGGED_USER);
        router.setPagePath(PagePath.SIGNIN_PAGE);
        return router;
    }
}
