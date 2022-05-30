package by.lukyanov.finaltask.command.impl.order;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.ParameterAttributeName;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CancelOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderServiceImpl orderService = new OrderServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(PagePath.TO_USER_ORDERS);
        String orderId = request.getParameter(ParameterAttributeName.ORDER_ID);
        try {
            if (orderService.cancelOrder(orderId)){
                router.setType(Router.Type.REDIRECT);
            } else {
                request.setAttribute(ParameterAttributeName.MESSAGE, "not canceled"); //todo add property message
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying cancel order", e);
            throw new CommandException(e);
        }
        return router;
    }
}
