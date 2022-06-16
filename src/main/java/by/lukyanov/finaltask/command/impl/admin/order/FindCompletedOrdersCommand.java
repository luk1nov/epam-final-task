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

public class FindCompletedOrdersCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderServiceImpl orderService = OrderServiceImpl.getInstance();
    private static final int POSTS_PER_PAGE = 10;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Router router = new Router(ADMIN_COMPLETED_ORDERS);
        String currentResultPage = request.getParameter(ParameterAttributeName.RESULT_PAGE);
        StringBuilder currentPage = new StringBuilder()
                .append(TO_ADMIN_COMPLETED_ORDERS)
                .append(RESULT_PAGE_ATTR);
        if(currentResultPage != null){
            currentPage.append(currentResultPage);
        }
        session.setAttribute(CURRENT_PAGE, currentPage.toString());
        try {
            int pagesCount = ResultCounter.countPages(orderService.countOrdersByStatus(OrderStatus.FINISHED), POSTS_PER_PAGE);
            request.setAttribute(PAGES_COUNT, pagesCount);
            request.setAttribute(RESULT_PAGE, currentResultPage);
            List<Order> orders = orderService.findOrdersByOrderStatus(OrderStatus.FINISHED, currentResultPage, POSTS_PER_PAGE);
            request.setAttribute(LIST, orders);
        } catch (ServiceException e) {
            logger.error("Command exception trying find finished orders", e);
            throw new CommandException(e);
        }
        return router;
    }
}
