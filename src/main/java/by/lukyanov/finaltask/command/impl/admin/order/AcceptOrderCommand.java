package by.lukyanov.finaltask.command.impl.admin.order;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.ParameterAttributeName;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.OrderStatus;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.lukyanov.finaltask.command.PagePath.TO_ADMIN_PROCESSING_ORDERS;

public class AcceptOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderServiceImpl orderService = new OrderServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(TO_ADMIN_PROCESSING_ORDERS);
        String orderId = request.getParameter(ParameterAttributeName.ORDER_ID);
        try{
            if(orderService.updateOrderStatus(OrderStatus.ACTIVE, orderId)){
                router.setType(Router.Type.REDIRECT);
            } else {
                request.setAttribute(ParameterAttributeName.MESSAGE, ""); //todo add property message
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying update order status", e);
            throw new CommandException(e);
        }
        return router;
    }
}
