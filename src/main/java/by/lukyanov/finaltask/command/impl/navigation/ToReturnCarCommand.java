package by.lukyanov.finaltask.command.impl.navigation;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.ParameterAttributeName;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import static by.lukyanov.finaltask.command.ParameterAttributeName.ORDER_ID;

public class ToReturnCarCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(PagePath.ORDER_REPORT);
        String orderId = request.getParameter(ORDER_ID);
        request.setAttribute(ORDER_ID, orderId);
        return router;
    }
}
