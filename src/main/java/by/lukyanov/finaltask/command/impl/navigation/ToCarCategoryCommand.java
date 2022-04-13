package by.lukyanov.finaltask.command.impl.navigation;

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

import static by.lukyanov.finaltask.command.PagePath.*;
import static by.lukyanov.finaltask.command.ParameterAndAttribute.*;

public class ToCarCategoryCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = new CarServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String catId = request.getParameter(CAR_CATEGORY).strip();
        try {
            List<Car> cars = carService.findCarsByCategoryId(catId);
            request.setAttribute(LIST, cars);
            router.setPagePath(CAR_CATEGORY_PAGE);
        } catch (ServiceException e) {
            logger.error("Command exception trying find all cars", e);
            throw new CommandException(e);
        }
        return router;
    }
}
