package by.lukyanov.finaltask.command.impl.common.navigation;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import static by.lukyanov.finaltask.command.PagePath.SIGN_UP_PAGE;

public class ToSignUpCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return new Router(SIGN_UP_PAGE);
    }
}
