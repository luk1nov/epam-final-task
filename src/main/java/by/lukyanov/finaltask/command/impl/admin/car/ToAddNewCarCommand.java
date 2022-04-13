package by.lukyanov.finaltask.command.impl.admin.car;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarCategoryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

import static by.lukyanov.finaltask.command.ParameterAndAttribute.LIST;


public class ToAddNewCarCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarCategoryServiceImpl carCategoryService = new CarCategoryServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        List<CarCategory> carCategoryList;
        try {
            carCategoryList = carCategoryService.findAllCarCategories();
            request.setAttribute(LIST, carCategoryList);
            router.setPagePath(PagePath.ADMIN_ADD_NEW_CAR);
        } catch (ServiceException e) {
            logger.error("Dao exception trying find all car categories");
        }
        return router;
    }
}
