package by.lukyanov.finaltask.command.impl.admin.order;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.ParameterAttributeName;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.OrderStatus;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.lukyanov.finaltask.command.Message.ORDER_ACCEPTED;
import static by.lukyanov.finaltask.command.Message.ORDER_NOT_ACCEPTED;
import static by.lukyanov.finaltask.command.ParameterAttributeName.CURRENT_PAGE;
import static by.lukyanov.finaltask.command.ParameterAttributeName.MESSAGE_ATTR;

public class AcceptOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderServiceImpl orderService = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);
        String orderId = request.getParameter(ParameterAttributeName.ORDER_ID);
        try{
            if(orderService.updateOrderStatus(OrderStatus.ACTIVE, orderId)){
                router.setPagePath(generateUrlWithAttr(currentPage, MESSAGE_ATTR, ORDER_ACCEPTED));
            } else {
                router.setPagePath(generateUrlWithAttr(currentPage, MESSAGE_ATTR, ORDER_NOT_ACCEPTED));
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying update order status", e);
            throw new CommandException(e);
        }
        return router;
    }
}
