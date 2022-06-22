package by.lukyanov.finaltask.command.impl.admin.car;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.Order;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarServiceImpl;
import by.lukyanov.finaltask.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.lukyanov.finaltask.command.Message.*;
import static by.lukyanov.finaltask.command.PagePath.TO_ADMIN_ALL_CARS;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;


public class DeleteCarCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = CarServiceImpl.getInstance();
    private static final OrderServiceImpl orderService = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        session.setAttribute(CURRENT_PAGE, TO_ADMIN_ALL_CARS);
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);
        String carId = request.getParameter(CAR_ID);
        try {
            List<Order> activeOrders = orderService.findActiveOrderDatesByCarId(carId);
            if (!activeOrders.isEmpty()){
                router.setPagePath(generateUrlWithAttr(currentPage, MESSAGE_ATTR, ACTIVE_ORDERS_WITH_THIS_CAR));
            } else if (!carService.deleteCarById(carId)){
                router.setPagePath(generateUrlWithAttr(currentPage, MESSAGE_ATTR, CAR_NOT_DELETED));
            } else {
                router.setPagePath(generateUrlWithAttr(currentPage, MESSAGE_ATTR, CAR_DELETED));
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying delete car", e);
            throw new CommandException(e);
        }
        return router;
    }
}
