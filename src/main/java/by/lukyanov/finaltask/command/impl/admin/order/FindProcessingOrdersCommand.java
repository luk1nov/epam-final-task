package by.lukyanov.finaltask.command.impl.admin.order;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.ParameterAttributeName;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.Order;
import by.lukyanov.finaltask.entity.OrderStatus;
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

public class FindProcessingOrdersCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderServiceImpl orderService = OrderServiceImpl.getInstance();
    private static final int POSTS_PER_PAGE = 10;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Router router = new Router(ADMIN_PROCESSING_ORDERS);
        request.setAttribute(MESSAGE, request.getParameter(MESSAGE));
        String currentResultPage = request.getParameter(ParameterAttributeName.RESULT_PAGE);
        session.setAttribute(CURRENT_PAGE, generateUrlWithAttr(TO_ADMIN_PROCESSING_ORDERS, RESULT_PAGE_ATTR, currentResultPage));
        try {
            int totalResultPages = ResultCounter.countPages(orderService.countOrdersByStatus(OrderStatus.PROCESSING), POSTS_PER_PAGE);
            request.setAttribute(PAGES_COUNT, totalResultPages);
            request.setAttribute(RESULT_PAGE, currentResultPage);
            List<Order> orderList = orderService.findOrdersByOrderStatus(OrderStatus.PROCESSING, currentResultPage, POSTS_PER_PAGE);
            request.setAttribute(ParameterAttributeName.LIST, orderList);
        } catch (ServiceException e) {
            logger.error("Command exception trying find processing orders", e);
            throw new CommandException(e);
        }
        return router;
    }
}
