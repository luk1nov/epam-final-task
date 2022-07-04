package by.lukyanov.finaltask.command.impl.admin.order;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.Order;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.OrderServiceImpl;
import by.lukyanov.finaltask.util.ResultCounter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.lukyanov.finaltask.command.PagePath.ADMIN_ALL_ORDERS;
import static by.lukyanov.finaltask.command.PagePath.TO_ADMIN_ALL_ORDERS;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;
import static by.lukyanov.finaltask.util.ResultCounter.countPages;

public class FinaAllOrdersCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderServiceImpl orderService = OrderServiceImpl.getInstance();
    private static final int POSTS_PER_PAGE = 10;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        request.setAttribute(MESSAGE, request.getParameter(MESSAGE));
        String currentResultPage = request.getParameter(RESULT_PAGE);
        session.setAttribute(CURRENT_PAGE, generateUrlWithAttr(TO_ADMIN_ALL_ORDERS, RESULT_PAGE_ATTR, currentResultPage));
        try {
            int pagesCount = countPages(orderService.countAllOrders(), POSTS_PER_PAGE);
            request.setAttribute(PAGES_COUNT, pagesCount);
            request.setAttribute(RESULT_PAGE, currentResultPage);
            List<Order> orderList = orderService.findAllOrders(currentResultPage, POSTS_PER_PAGE);
            request.setAttribute(LIST, orderList);
        } catch (ServiceException e) {
            logger.error("Command exception trying find all orders", e);
            throw new CommandException(e);
        }
        return new Router(ADMIN_ALL_ORDERS);
    }
}
