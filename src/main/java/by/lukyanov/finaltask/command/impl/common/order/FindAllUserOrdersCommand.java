package by.lukyanov.finaltask.command.impl.common.order;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.Order;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.OrderServiceImpl;
import by.lukyanov.finaltask.util.ResultCounter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.lukyanov.finaltask.command.PagePath.*;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;
import static by.lukyanov.finaltask.util.ResultCounter.countPages;

public class FindAllUserOrdersCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderServiceImpl orderService = OrderServiceImpl.getInstance();
    private static final int POSTS_PER_PAGE = 10;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(SIGN_IN_PAGE);
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute(LOGGED_USER);
        request.setAttribute(MESSAGE, request.getParameter(MESSAGE));
        String currentResultPage = request.getParameter(RESULT_PAGE);
        try {
            int pagesCount = countPages(orderService.countOrdersByUserId(loggedUser.getId()), POSTS_PER_PAGE);
            List<Order> orderList = orderService.findAllOrdersByUserId(loggedUser.getId(), currentResultPage, POSTS_PER_PAGE);
            request.setAttribute(PAGES_COUNT, pagesCount);
            request.setAttribute(RESULT_PAGE, currentResultPage);
            request.setAttribute(LIST, orderList);
            router.setPagePath(USER_ACCOUNT_ORDERS);
            session.setAttribute(CURRENT_PAGE, generateUrlWithAttr(TO_FIND_ALL_USER_ORDERS, RESULT_PAGE_ATTR, currentResultPage));
        } catch (ServiceException e) {
            logger.error("Command exception trying find all user orders", e);
            throw new CommandException(e);
        }
        return router;
    }
}
