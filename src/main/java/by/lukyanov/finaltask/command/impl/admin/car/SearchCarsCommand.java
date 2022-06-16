package by.lukyanov.finaltask.command.impl.admin.car;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Message;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.lukyanov.finaltask.command.PagePath.*;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class SearchCarsCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = CarServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Router router = new Router(TO_ADMIN_ALL_CARS);
        request.setAttribute(MESSAGE, request.getParameter(MESSAGE));
        String searchQuery = request.getParameter(SEARCH);
        try {
            if(searchQuery != null && !searchQuery.isBlank()) {
                session.setAttribute(CURRENT_PAGE, generateUrlWithAttr(TO_ADMIN_SEARCH_CAR, SEARCH_ATTR, searchQuery));
                router.setPagePath(ADMIN_SEARCH_CARS_RESULTS);
                List<Car> cars = carService.searchCars(searchQuery);
                request.setAttribute(LIST, cars);
                request.setAttribute(SEARCH, searchQuery.trim());
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying search cars", e);
            throw new CommandException(e);
        }
        return router;
    }
}
