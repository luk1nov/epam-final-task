package by.lukyanov.finaltask.command.impl.admin.carcategory;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.ParameterAndAttribute;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarCategoryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.lukyanov.finaltask.command.PagePath.*;

public class DeleteCarCategoryCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarCategoryServiceImpl carCategoryService = new CarCategoryServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String carCatId = request.getParameter(ParameterAndAttribute.CAR_CATEGORY_ID);
        try{
            if(carCategoryService.deleteCarCategory(carCatId)){
                router.setPagePath(ADMIN_SUCCESS_PAGE);
                router.setType(Router.Type.REDIRECT);
            } else {
                router.setPagePath(ADMIN_FAIL_PAGE);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying delete car category by id");
        }
        return router;
    }
}
