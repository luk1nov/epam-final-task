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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class FindAllUserOrdersCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderServiceImpl orderService = new OrderServiceImpl();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(PagePath.SIGNUP_PAGE);
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute(ParameterAttributeName.LOGGED_USER);
        if (loggedUser != null){
            try {
                List<Order> orderList = orderService.findAllOrdersByUserId(loggedUser.getId());
                request.setAttribute(ParameterAttributeName.LIST, orderList);
                router.setPagePath(PagePath.USER_ACCOUNT_ORDERS);
            } catch (ServiceException e) {
                logger.error("Command exception trying find all user orders", e);
                throw new CommandException(e);
            }
        }

        return router;
    }
}
