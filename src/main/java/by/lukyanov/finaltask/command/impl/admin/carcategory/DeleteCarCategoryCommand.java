package by.lukyanov.finaltask.command.impl.admin.carcategory;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.command.impl.util.ContextCategoryUploader;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarCategoryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.lukyanov.finaltask.command.Message.*;
import static by.lukyanov.finaltask.command.PagePath.TO_ADMIN_ALL_CATEGORIES;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class DeleteCarCategoryCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarCategoryServiceImpl carCategoryService = CarCategoryServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ContextCategoryUploader uploader = ContextCategoryUploader.getInstance();
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);
        String carCatId = request.getParameter(CAR_CATEGORY_ID);
        try{
            if(carCategoryService.deleteCarCategory(carCatId)){
                router.setPagePath(generateUrlWithAttr(TO_ADMIN_ALL_CATEGORIES, MESSAGE_ATTR, CATEGORY_DELETED));
                uploader.uploadCategories(request, true);
            } else {
                router.setPagePath(generateUrlWithAttr(TO_ADMIN_ALL_CATEGORIES, MESSAGE_ATTR, CATEGORY_NOT_DELETED));
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying delete car category by id");
        }
        return router;
    }
}
