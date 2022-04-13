package by.lukyanov.finaltask.command.impl.admin.carcategory;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.ParameterAndAttribute;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarCategoryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.lukyanov.finaltask.command.PagePath.FAIL_PAGE;

public class ToEditCarCategoryCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarCategoryServiceImpl carCategoryService = new CarCategoryServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String carCategoryId = request.getParameter(ParameterAndAttribute.CAR_CATEGORY_ID);
        try {
            Optional<CarCategory> optionalCarCategory;
            optionalCarCategory = carCategoryService.findCarCategoryById(carCategoryId);
            if (optionalCarCategory.isPresent()){
                request.setAttribute(ParameterAndAttribute.CAR_CATEGORY, optionalCarCategory.get());
                router.setPagePath(PagePath.ADMIN_EDIT_CAR_CATEGORY);
            } else {
                request.setAttribute(ParameterAndAttribute.MESSAGE, "car category not found");
                router.setPagePath(FAIL_PAGE);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying find category by id", e);
            throw new CommandException(e);
        }
        return router;
    }
}
