package by.lukyanov.finaltask.command.impl.admin.carcategory;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.command.impl.util.ContextCategoryUploader;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.lukyanov.finaltask.command.PagePath.ADMIN_ALL_CAR_CATEGORIES;
import static by.lukyanov.finaltask.command.PagePath.TO_ADMIN_ALL_CATEGORIES;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class FindAllCarCategoriesCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ContextCategoryUploader uploader = ContextCategoryUploader.getInstance();
        HttpSession session = request.getSession();
        request.setAttribute(MESSAGE, request.getParameter(MESSAGE));
        session.setAttribute(CURRENT_PAGE, TO_ADMIN_ALL_CATEGORIES);
        try {
            uploader.uploadCategories(request, false);
        } catch (ServiceException e) {
            logger.error("Service exception trying find all categories", e);
            throw new CommandException(e);
        }
        return new Router(ADMIN_ALL_CAR_CATEGORIES);
    }
}
