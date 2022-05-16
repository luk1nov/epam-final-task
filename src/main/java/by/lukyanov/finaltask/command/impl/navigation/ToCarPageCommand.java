package by.lukyanov.finaltask.command.impl.navigation;

import by.lukyanov.finaltask.command.*;
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

import static by.lukyanov.finaltask.command.PagePath.CAR_PAGE;
import static by.lukyanov.finaltask.command.PagePath.TO_CAR_PAGE;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class ToCarPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carServiceImpl = new CarServiceImpl();
    private static final OrderServiceImpl orderServiceImpl = new OrderServiceImpl();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(PagePath.SIGNIN_PAGE);
        String carId = request.getParameter(ParameterAttributeName.CAR_ID);
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(ParameterAttributeName.CURRENT_PAGE);
        User loggedUser = (User) session.getAttribute(LOGGED_USER);
        if (loggedUser != null){
            try {
                Optional<Car> optionalCar = carServiceImpl.findCarById(carId);
                if (optionalCar.isPresent()){
                    Car car = optionalCar.get();
                    List<Order> orderList = orderServiceImpl.findActiveOrderDatesByCarId(car.getId());
                    request.setAttribute(LIST, orderList);
                    request.setAttribute(CAR, optionalCar.get());
                    String carPagePath = TO_CAR_PAGE + "&carId=" + carId;
                    session.setAttribute(CURRENT_PAGE, carPagePath);
                    router.setPagePath(CAR_PAGE);
                    logger.debug("router " + router.getPagePath() + " " + router.getType() );
                } else {
                    request.setAttribute(MESSAGE, Message.CAR_NOT_FOUND);
                    router.setPagePath(currentPage);
                }
            } catch (ServiceException e) {
                logger.error("Command exception trying navigate to car page", e);
                throw new CommandException(e);
            }
        }
        logger.debug("session " + session.getAttribute(CURRENT_PAGE));
        return router;
    }
}
