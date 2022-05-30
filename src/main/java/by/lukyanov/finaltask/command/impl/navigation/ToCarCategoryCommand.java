package by.lukyanov.finaltask.command.impl.navigation;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarCategoryServiceImpl;
import by.lukyanov.finaltask.model.service.impl.CarServiceImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static by.lukyanov.finaltask.command.PagePath.*;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class ToCarCategoryCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = new CarServiceImpl();
    private static final CarCategoryServiceImpl categoryService = new CarCategoryServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String catId = request.getParameter(CAR_CATEGORY).strip();
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Router router = new Router(currentPage);
        String categoryPage = TO_CAR_CAR_CATEGORY_PAGE + CAR_CATEGORY_ATTR + catId;
        try {
            Optional<CarCategory> optionalCarCategory = categoryService.findCarCategoryById(catId);
            if(optionalCarCategory.isPresent()){
                CarCategory category = optionalCarCategory.get();
                request.setAttribute(CAR_CATEGORY_TITLE, category.getTitle());
                List<Car> cars = carService.findCarsByCategoryId(catId);
                request.setAttribute(LIST, cars);
                router.setPagePath(CAR_CATEGORY_PAGE);
                session.setAttribute(CURRENT_PAGE, categoryPage);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying find all cars", e);
            throw new CommandException(e);
        }
        return router;
    }
}
