package by.lukyanov.finaltask.command.impl.admin.carcategory;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarCategoryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.lukyanov.finaltask.command.Message.CATEGORY_NOT_EXISTS;
import static by.lukyanov.finaltask.command.PagePath.*;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class ToEditCarCategoryCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarCategoryServiceImpl carCategoryService = CarCategoryServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Router router = new Router();
        String carCategoryId = request.getParameter(CAR_CATEGORY_ID);
        try {
            Optional<CarCategory> optionalCarCategory = carCategoryService.findCarCategoryById(carCategoryId);
            if (optionalCarCategory.isPresent()){
                String currentPage = generateUrlWithAttr(TO_ADMIN_EDIT_CATEGORY, CAR_CATEGORY_ATTR, carCategoryId);
                session.setAttribute(CURRENT_PAGE, currentPage);
                request.setAttribute(CAR_CATEGORY, optionalCarCategory.get());
                router.setPagePath(ADMIN_EDIT_CAR_CATEGORY);
            } else {
                router.setPagePath(generateUrlWithAttr(TO_ADMIN_ALL_CATEGORIES, MESSAGE_ATTR, CATEGORY_NOT_EXISTS));
                router.setType(Router.Type.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying find category by id", e);
            throw new CommandException(e);
        }
        return router;
    }
}
