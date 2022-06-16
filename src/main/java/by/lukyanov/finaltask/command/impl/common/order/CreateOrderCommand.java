package by.lukyanov.finaltask.command.impl.common.order;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Message;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.entity.Order;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.entity.UserStatus;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarServiceImpl;
import by.lukyanov.finaltask.model.service.impl.OrderServiceImpl;
import by.lukyanov.finaltask.model.service.impl.UserServiceImpl;
import by.lukyanov.finaltask.util.DateRangeParser;
import by.lukyanov.finaltask.validation.impl.ValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static by.lukyanov.finaltask.command.Message.*;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;
import static by.lukyanov.finaltask.command.PagePath.*;
import static by.lukyanov.finaltask.util.DateRangeParser.BEGIN_DATE_INDEX;
import static by.lukyanov.finaltask.util.DateRangeParser.END_DATE_INDEX;

public class CreateOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = CarServiceImpl.getInstance();
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();
    private static final OrderServiceImpl orderService = OrderServiceImpl.getInstance();
    private static final ValidatorImpl validator = ValidatorImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        User loggedUser = (User) session.getAttribute(LOGGED_USER);
        String orderDateRange = request.getParameter(ORDER_DATE_RANGE);
        String carId = request.getParameter(CAR_ID);
        Router router = new Router(currentPage);
        try {
            if (loggedUser != null){
                Optional<User> optionalUser = userService.findUserById(String.valueOf(loggedUser.getId()));
                Optional<Car> optionalCar = carService.findCarById(carId);
                if(optionalUser.isEmpty() || optionalCar.isEmpty()){
                    String path = optionalUser.isEmpty() ? TO_LOG_OUT : MAIN_PAGE;
                    router.setPagePath(path);
                } else if (optionalUser.get().getStatus() != UserStatus.ACTIVE){
                    router.setPagePath(generateUrlWithAttr(TO_GO_USER_ACCOUNT, MESSAGE_ATTR, USER_NOT_ACTIVE));
                    router.setType(Router.Type.REDIRECT);
                } else if(!validator.isValidDateRange(orderDateRange)){
                    request.setAttribute(MESSAGE, INVALID_DATA);
                } else if(compareBalanceAndPrice(optionalCar.get(), optionalUser.get(), orderDateRange) < 0){
                    request.setAttribute(MESSAGE, NOT_ENOUGH_MONEY);
                } else {
                    router = addOrder(optionalCar.get(), optionalUser.get(), orderDateRange);
                }
            } else {
                router.setPagePath(SIGNIN_PAGE);
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

    private int compareBalanceAndPrice(Car car, User user, String orderDateRange){
        List<LocalDate> dateRange = DateRangeParser.parse(orderDateRange);
        LocalDate beginDate = dateRange.get(BEGIN_DATE_INDEX);
        LocalDate endDate = dateRange.size() > 1 ? dateRange.get(END_DATE_INDEX) : dateRange.get(BEGIN_DATE_INDEX);
        int orderDays = DateRangeParser.countDays(beginDate, endDate);
        BigDecimal orderPrice = orderService.calculateOrderPrice(car, orderDays);
        return user.getBalance().compareTo(orderPrice);
    }
}
