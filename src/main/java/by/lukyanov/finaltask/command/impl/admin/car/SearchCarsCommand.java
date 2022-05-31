package by.lukyanov.finaltask.command.impl.admin.car;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.lukyanov.finaltask.command.PagePath.ADMIN_SEARCH_CARS_RESULTS;
import static by.lukyanov.finaltask.command.ParameterAttributeName.LIST;
import static by.lukyanov.finaltask.command.ParameterAttributeName.SEARCH;

public class SearchCarsCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = new CarServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(ADMIN_SEARCH_CARS_RESULTS);
        String searchQuery = request.getParameter(SEARCH);
        try {
            List<Car> cars = carService.searchCars(searchQuery);
            request.setAttribute(LIST, cars);
            request.setAttribute(SEARCH, searchQuery.trim());
        } catch (ServiceException e) {
            logger.error("Command exception trying search cars", e);
            throw new CommandException(e);
        }
        return router;
    }
}
