package by.lukyanov.finaltask.command.impl.common.navigation;

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

import static by.lukyanov.finaltask.command.PagePath.CAR_CATEGORY_PAGE;
import static by.lukyanov.finaltask.command.PagePath.TO_ALL_CARS_CATEGORY_PAGE;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;
import static by.lukyanov.finaltask.util.ResultCounter.countPages;

public class ToAllCarsCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = CarServiceImpl.getInstance();
    private static final int POSTS_PER_PAGE = 4;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentResultPage = request.getParameter(RESULT_PAGE);
        try {
            int pagesCount = countPages(carService.countAllCars(), POSTS_PER_PAGE);
            request.setAttribute(PAGES_COUNT, pagesCount);
            request.setAttribute(RESULT_PAGE, currentResultPage);
            List<Car> cars = carService.findAllCars(currentResultPage, POSTS_PER_PAGE);
            request.setAttribute(LIST, cars);
            session.setAttribute(CURRENT_PAGE, generateUrlWithAttr(TO_ALL_CARS_CATEGORY_PAGE, RESULT_PAGE_ATTR, currentResultPage));
        } catch (ServiceException e) {
            logger.error("Command exception trying find all cars", e);
            throw new CommandException(e);
        }
        return new Router(CAR_CATEGORY_PAGE);
    }
}
