package by.lukyanov.finaltask.command.impl.order;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.entity.UserStatus;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarServiceImpl;
import by.lukyanov.finaltask.model.service.impl.OrderServiceImpl;
import by.lukyanov.finaltask.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.lukyanov.finaltask.command.ParameterAttributeName.*;
import static by.lukyanov.finaltask.command.PagePath.*;

public class CreateOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = new CarServiceImpl();
    private static final UserServiceImpl userService = new UserServiceImpl();
    private static final OrderServiceImpl orderService = new OrderServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute(LOGGED_USER);
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        String orderDateRange = request.getParameter(ORDER_DATE_RANGE);
        String carId = request.getParameter(CAR_ID);
        Router router = new Router(SIGNIN_PAGE);
        try {
            if (loggedUser != null){
                Optional<User> optionalUser = userService.findUserById(String.valueOf(loggedUser.getId()));
                Optional<Car> optionalCar = carService.findCarById(carId);
                if(optionalUser.isEmpty() || optionalCar.isEmpty()){
                    String destinationPage = optionalUser.isEmpty() ? TO_LOG_OUT : MAIN_PAGE;
                    router.setPagePath(destinationPage);
                } else if (optionalUser.get().getStatus() != UserStatus.ACTIVE){
                    router.setPagePath(currentPage);
                    request.setAttribute(MESSAGE, ""); //todo add property message
                } else {
                    Car car = optionalCar.get();
                    User user = optionalUser.get();
                    router = addOrder(car, user, orderDateRange);
                }
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying create order", e);
            throw new CommandException(e);
        }
        return router;
    }

    private Router addOrder(Car car, User user, String orderDateRange) throws ServiceException {
        Router router = new Router();
        if (orderService.addOrder(car, user, orderDateRange)) {
            router.setType(Router.Type.REDIRECT);
            router.setPagePath(SUCCESSFUL_ORDER);
        } else {
            router.setPagePath(FAILED_ORDER);
        }
        return router;
    }
}
