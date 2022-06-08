package by.lukyanov.finaltask.command.impl.admin.order;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.Order;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.lukyanov.finaltask.command.PagePath.ADMIN_SEARCH_ORDERS_RESULTS;
import static by.lukyanov.finaltask.command.PagePath.TO_ADMIN_ALL_ORDERS;
import static by.lukyanov.finaltask.command.ParameterAttributeName.LIST;
import static by.lukyanov.finaltask.command.ParameterAttributeName.SEARCH;

public class SearchOrdersCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderServiceImpl orderService = new OrderServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(TO_ADMIN_ALL_ORDERS);
        String searchQuery = request.getParameter(SEARCH);
        try {
            if(searchQuery != null && !searchQuery.isBlank()){
                router.setPagePath(ADMIN_SEARCH_ORDERS_RESULTS);
                List<Order> orders = orderService.searchOrders(searchQuery);
                request.setAttribute(LIST, orders);
                request.setAttribute(SEARCH, searchQuery.trim());
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying search orders", e);
            throw new CommandException(e);
        }
        return router;
    }
}
