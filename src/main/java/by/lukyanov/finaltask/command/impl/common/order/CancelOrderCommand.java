package by.lukyanov.finaltask.command.impl.common.order;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.ParameterAttributeName;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.OrderServiceImpl;
import by.lukyanov.finaltask.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.lukyanov.finaltask.command.Message.ORDER_CANCELED;
import static by.lukyanov.finaltask.command.Message.ORDER_NOT_CANCELED;
import static by.lukyanov.finaltask.command.PagePath.TO_LOG_OUT;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class CancelOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderServiceImpl orderService = OrderServiceImpl.getInstance();
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        User loggedUser = (User) session.getAttribute(LOGGED_USER);
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);
        String orderId = request.getParameter(ParameterAttributeName.ORDER_ID);
        try {
            if (orderService.cancelOrder(orderId)){
                String userId = String.valueOf(loggedUser.getId());
                Optional<User> optionalUser = userService.findUserById(userId);
                if (optionalUser.isPresent()){
                    session.setAttribute(LOGGED_USER, optionalUser.get());
                    router.setPagePath(generateUrlWithAttr(currentPage, MESSAGE_ATTR, ORDER_CANCELED));
                } else {
                    router.setPagePath(TO_LOG_OUT);
                }
            } else {
                router.setPagePath(generateUrlWithAttr(currentPage, MESSAGE_ATTR, ORDER_NOT_CANCELED));
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying cancel order", e);
            throw new CommandException(e);
        }
        return router;
    }
}
