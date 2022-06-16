package by.lukyanov.finaltask.command.impl.admin.carcategory;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.command.impl.util.ContextCategoryUploader;
import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarCategoryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.lukyanov.finaltask.command.Message.*;
import static by.lukyanov.finaltask.command.PagePath.*;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class EditCarCategoryCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarCategoryServiceImpl carCategoryService = CarCategoryServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ContextCategoryUploader uploader = ContextCategoryUploader.getInstance();
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Router router = new Router(currentPage);
        String id = request.getParameter(CAR_CATEGORY_ID);
        String title = request.getParameter(CAR_CATEGORY_TITLE);
        try {
            if (checkDuplicateByTitle(title, id)){
                request.setAttribute(MESSAGE, CATEGORY_EXISTS);
            } else if(carCategoryService.updateCarCategory(id, title)){
                router.setPagePath(generateUrlWithAttr(TO_ADMIN_ALL_CATEGORIES, MESSAGE_ATTR, CATEGORY_EDITED));
                router.setType(Router.Type.REDIRECT);
                uploader.uploadCategories(request, true);
            } else {
                request.setAttribute(MESSAGE, CATEGORY_NOT_EDITED);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying edit car category");
            throw new CommandException(e);
        }
        return router;
    }

    private boolean checkDuplicateByTitle(String title, String catId) throws ServiceException {
        boolean isDuplicate = false;
        Optional<CarCategory> optionalCarCategory = carCategoryService.findCarCategoryByTitle(title);
        if(optionalCarCategory.isPresent()){
            CarCategory category = optionalCarCategory.get();
            isDuplicate = !catId.equals(String.valueOf(category.getId()));
        }
        return isDuplicate;
    }
}
