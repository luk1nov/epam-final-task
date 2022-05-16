package by.lukyanov.finaltask.command.impl.navigation;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import static by.lukyanov.finaltask.command.PagePath.ADMIN_DECLINE_ORDER;
import static by.lukyanov.finaltask.command.ParameterAttributeName.ORDER_ID;

public class ToDeclineOrderCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(ADMIN_DECLINE_ORDER);
        String orderId = request.getParameter(ORDER_ID);
        request.setAttribute(ORDER_ID, orderId);
        return router;
    }
}
