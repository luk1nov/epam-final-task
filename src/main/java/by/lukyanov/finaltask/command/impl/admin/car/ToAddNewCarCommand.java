package by.lukyanov.finaltask.command.impl.admin.car;

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

import java.util.List;

import static by.lukyanov.finaltask.command.PagePath.ADMIN_ADD_NEW_CAR;
import static by.lukyanov.finaltask.command.PagePath.TO_ADMIN_ADD_NEW_CAR;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;


public class ToAddNewCarCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ContextCategoryUploader uploader = ContextCategoryUploader.getInstance();
        HttpSession session = request.getSession();
        session.setAttribute(CURRENT_PAGE, TO_ADMIN_ADD_NEW_CAR);
        try {
            uploader.uploadCategories(request, false);
        } catch (ServiceException e) {
            logger.error("Dao exception trying find all car categories");
        }
        return new Router(ADMIN_ADD_NEW_CAR);
    }
}
