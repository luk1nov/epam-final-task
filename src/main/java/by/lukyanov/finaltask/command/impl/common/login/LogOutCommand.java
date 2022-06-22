package by.lukyanov.finaltask.command.impl.common.login;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.lukyanov.finaltask.command.PagePath.SIGN_IN_PAGE;
import static by.lukyanov.finaltask.command.ParameterAttributeName.LOGGED_USER;
import static by.lukyanov.finaltask.command.ParameterAttributeName.MESSAGE;

public class LogOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        request.setAttribute(MESSAGE, request.getParameter(MESSAGE));
        session.removeAttribute(LOGGED_USER);
        return new Router(SIGN_IN_PAGE);
    }
}
