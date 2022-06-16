package by.lukyanov.finaltask.command.impl.navigation;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.command.impl.util.ContextCategoryUploader;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.lukyanov.finaltask.command.PagePath.CATEGORIES_PAGE;

public class ToAllCategoriesCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ContextCategoryUploader uploader = ContextCategoryUploader.getInstance();
        try {
            uploader.uploadCategories(request, false);
        } catch (ServiceException e) {
            logger.error("Command exception trying upload categories", e);
            throw new CommandException(e);
        }
        return new Router(CATEGORIES_PAGE);
    }
}
