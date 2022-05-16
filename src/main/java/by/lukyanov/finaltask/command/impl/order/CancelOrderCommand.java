package by.lukyanov.finaltask.command.impl.order;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.ParameterAttributeName;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.Order;
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

import static by.lukyanov.finaltask.command.ParameterAttributeName.LOGGED_USER;

public class CancelOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderServiceImpl orderService = new OrderServiceImpl();
    private static final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute(LOGGED_USER);
        Router router = new Router(PagePath.TO_USER_ORDERS);
        String orderId = request.getParameter(ParameterAttributeName.ORDER_ID);
        try {
            Optional<Order> optionalOrder = orderService.findOrderById(orderId);
            if(optionalOrder.isPresent()){
                logger.debug("order found");
                boolean isOrderCanceled = orderService.cancelOrder(optionalOrder.get());
                if (isOrderCanceled){
                    Optional<User> user = userService.findUserById(String.valueOf(loggedUser.getId()));
                    if (user.isPresent()){
                        session.setAttribute(LOGGED_USER, user.get());
                    } else {
                        session.removeAttribute(LOGGED_USER);
                        router.setPagePath(PagePath.TO_LOG_OUT);
                    }
                    router.setType(Router.Type.REDIRECT);
                } else {
                    request.setAttribute(ParameterAttributeName.MESSAGE, "not canceled"); //todo add property message
                }
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying cancel order", e);
            throw new CommandException(e);
        }
        return router;
    }
}
