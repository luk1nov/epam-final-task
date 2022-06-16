package by.lukyanov.finaltask.command.impl.common.navigation;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import static by.lukyanov.finaltask.command.PagePath.SIGNIN_PAGE;
import static by.lukyanov.finaltask.command.ParameterAttributeName.MESSAGE;

public class ToSignInCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        request.setAttribute(MESSAGE, request.getParameter(MESSAGE));
        return new Router(SIGNIN_PAGE);
    }
}
