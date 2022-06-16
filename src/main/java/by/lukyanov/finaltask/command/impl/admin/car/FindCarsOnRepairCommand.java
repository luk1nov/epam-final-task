package by.lukyanov.finaltask.command.impl.admin.car;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarServiceImpl;
import by.lukyanov.finaltask.util.ResultCounter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.lukyanov.finaltask.command.PagePath.*;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class FindCarsOnRepairCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = CarServiceImpl.getInstance();
    private static final int POSTS_PER_PAGE = 10;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Router router = new Router(ADMIN_REPAIRING_CARS);
        String currentResultPage = request.getParameter(RESULT_PAGE);
        request.setAttribute(MESSAGE, request.getParameter(MESSAGE));
        session.setAttribute(CURRENT_PAGE, generateUrlWithAttr(TO_ADMIN_REPAIRING_CARS, RESULT_PAGE_ATTR, currentResultPage));
        try {
            int pagesCount = ResultCounter.countPages(carService.countAllCarsByActive(false), POSTS_PER_PAGE);
            request.setAttribute(PAGES_COUNT, pagesCount);
            request.setAttribute(RESULT_PAGE, currentResultPage);
            List<Car> cars = carService.findCarsByActiveStatus(false, currentResultPage, POSTS_PER_PAGE);
            request.setAttribute(LIST, cars);
        } catch (ServiceException e) {
            logger.error("Command exception trying find cars on repair", e);
            throw new CommandException(e);
        }
        return router;
    }
}
