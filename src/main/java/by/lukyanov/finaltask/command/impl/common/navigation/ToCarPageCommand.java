package by.lukyanov.finaltask.command.impl.common.navigation;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.entity.Order;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarServiceImpl;
import by.lukyanov.finaltask.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static by.lukyanov.finaltask.command.Message.CAR_NOT_EXISTS;
import static by.lukyanov.finaltask.command.PagePath.*;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class ToCarPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carServiceImpl = CarServiceImpl.getInstance();
    private static final OrderServiceImpl orderServiceImpl = OrderServiceImpl.getInstance();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String carId = request.getParameter(CAR_ID);
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        User loggedUser = (User) session.getAttribute(LOGGED_USER);
        Router router = new Router(currentPage);
        if (loggedUser != null){
            try {
                Optional<Car> optionalCar = carServiceImpl.findCarById(carId);
                if (optionalCar.isPresent()){
                    Car car = optionalCar.get();
                    List<Order> orderList = orderServiceImpl.findActiveOrderDatesByCarId(String.valueOf(car.getId()));
                    request.setAttribute(LIST, orderList);
                    request.setAttribute(CAR, optionalCar.get());
                    router.setPagePath(CAR_PAGE);
                    session.setAttribute(CURRENT_PAGE, generateUrlWithAttr(TO_CAR_PAGE, CAR_ID_ATTR, carId));
                } else {
                    request.setAttribute(MESSAGE, CAR_NOT_EXISTS);
                }
            } catch (ServiceException e) {
                logger.error("Command exception trying navigate to car page", e);
                throw new CommandException(e);
            }
        } else {
            router.setPagePath(TO_LOG_OUT);
        }
        return router;
    }
}
