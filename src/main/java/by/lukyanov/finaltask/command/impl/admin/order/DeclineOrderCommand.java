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

import static by.lukyanov.finaltask.command.Message.ORDER_DECLINED;
import static by.lukyanov.finaltask.command.Message.ORDER_NOT_DECLINED;
import static by.lukyanov.finaltask.command.PagePath.TO_ADMIN_PROCESSING_ORDERS;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class DeclineOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderServiceImpl orderService = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Router router = new Router(currentPage);
        String orderMessage = request.getParameter(ORDER_MESSAGE);
        String orderId = request.getParameter(ORDER_ID);
        try {
            if(orderService.cancelOrder(orderId, orderMessage)){
                router.setType(Router.Type.REDIRECT);
                router.setPagePath(generateUrlWithAttr(TO_ADMIN_PROCESSING_ORDERS, MESSAGE_ATTR, ORDER_DECLINED));
            } else {
                request.setAttribute(MESSAGE, ORDER_NOT_DECLINED);
                request.setAttribute(ORDER_MESSAGE, orderMessage);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying decline order", e);
            throw new CommandException(e);
        }
        return router;
    }
}
