package by.lukyanov.finaltask.command.impl.admin.order;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.lukyanov.finaltask.command.Message.ORDER_DELETED;
import static by.lukyanov.finaltask.command.Message.ORDER_NOT_DELETED;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class DeleteOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderServiceImpl orderService = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Router router = new Router(Router.Type.REDIRECT, currentPage);
        String orderId = request.getParameter(ORDER_ID);
        try {
            if(orderService.deleteOrder(orderId)){
                router.setPagePath(generateUrlWithAttr(currentPage, MESSAGE_ATTR, ORDER_DELETED));
            } else {
                router.setPagePath(generateUrlWithAttr(currentPage, MESSAGE_ATTR, ORDER_NOT_DELETED));
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying delete order", e);
            throw new CommandException(e);
        }
        return router;
    }
}
