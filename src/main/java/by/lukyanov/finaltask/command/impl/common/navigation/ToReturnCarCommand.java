package by.lukyanov.finaltask.command.impl.common.navigation;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.Order;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.lukyanov.finaltask.command.Message.ORDER_NOT_FOUND;
import static by.lukyanov.finaltask.command.PagePath.TO_GO_RETURN_CAR;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class ToReturnCarCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderServiceImpl orderServiceImpl = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Router router = new Router(PagePath.ORDER_REPORT);
        String orderId = request.getParameter(ORDER_ID);
        try {
            Optional<Order> optionalOrder = orderServiceImpl.findOrderById(orderId);
            if(optionalOrder.isPresent()){
                request.setAttribute(ORDER_ID, orderId);
                session.setAttribute(CURRENT_PAGE, generateUrlWithAttr(TO_GO_RETURN_CAR, ORDER_ID_ATTR, orderId));
            } else {
                router.setType(Router.Type.REDIRECT);
                router.setPagePath(generateUrlWithAttr(currentPage, MESSAGE_ATTR, ORDER_NOT_FOUND));
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying go to return car page", e);
            throw new CommandException(e);
        }
        return router;
    }
}
