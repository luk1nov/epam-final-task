package by.lukyanov.finaltask.command.impl.admin.order;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.ParameterAttributeName;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.lukyanov.finaltask.command.PagePath.TO_ADMIN_PROCESSING_ORDERS;
import static by.lukyanov.finaltask.command.ParameterAttributeName.ORDER_ID;
import static by.lukyanov.finaltask.command.ParameterAttributeName.ORDER_MESSAGE;

public class DeclineOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderServiceImpl orderService = new OrderServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(TO_ADMIN_PROCESSING_ORDERS);
        String orderMessage = request.getParameter(ORDER_MESSAGE);
        String orderId = request.getParameter(ORDER_ID);
        try {
            if(orderService.cancelOrder(orderId, orderMessage)){
                router.setType(Router.Type.REDIRECT);
            } else {
                request.setAttribute(ParameterAttributeName.MESSAGE, ""); //todo add property message
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying decline order", e);
            throw new CommandException(e);
        }
        return router;
    }
}
