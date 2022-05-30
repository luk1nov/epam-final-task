package by.lukyanov.finaltask.command.impl.admin.order;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.ParameterAttributeName;
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
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class FinaAllOrdersCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderServiceImpl orderService = new OrderServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.setAttribute(CURRENT_PAGE, PagePath.TO_ADMIN_ALL_ORDERS);
        Router router = new Router(ADMIN_ALL_ORDERS);
        String currentResultPage = request.getParameter(ParameterAttributeName.RESULT_PAGE);
        try {
            int pagesCount = ResultCounter.countPages(orderService.countAllOrders());
            if(currentResultPage == null || currentResultPage.isBlank()){
                currentResultPage = "1";
            }
            request.setAttribute(PAGES_COUNT, pagesCount);
            request.setAttribute(RESULT_PAGE, currentResultPage);
            List<Order> orderList = orderService.findAllOrders(currentResultPage);
            request.setAttribute(LIST, orderList);
        } catch (ServiceException e) {
            logger.error("Command exception trying find all orders", e);
            throw new CommandException(e);
        }
        return router;
    }
}
