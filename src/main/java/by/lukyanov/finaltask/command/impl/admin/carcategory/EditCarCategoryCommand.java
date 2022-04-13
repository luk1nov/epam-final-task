package by.lukyanov.finaltask.command.impl.admin.carcategory;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarCategoryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.lukyanov.finaltask.command.PagePath.*;
import static by.lukyanov.finaltask.command.ParameterAndAttribute.*;

public class EditCarCategoryCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarCategoryServiceImpl carCategoryService = new CarCategoryServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String id = request.getParameter(CAR_CATEGORY_ID).strip();
        String title = request.getParameter(CAR_CATEGORY_TITLE).strip();
        try {
            if(carCategoryService.updateCarCategory(id, title)){
                router.setPagePath(SUCCESS_PAGE);
                router.setType(Router.Type.REDIRECT);
            } else {
                router.setPagePath(FAIL_PAGE);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying edit car category");
        }
        return router;
    }
}
