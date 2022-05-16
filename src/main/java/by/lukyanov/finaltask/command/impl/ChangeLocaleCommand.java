package by.lukyanov.finaltask.command.impl;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.ParameterAttributeName;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.validation.impl.ValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.lukyanov.finaltask.command.ParameterAttributeName.CURRENT_PAGE;
import static by.lukyanov.finaltask.command.ParameterAttributeName.LOCALE;

public class ChangeLocaleCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ValidatorImpl validator = ValidatorImpl.getInstance();
        Router router = new Router();
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        logger.debug(currentPage);
        String language = request.getParameter(ParameterAttributeName.LANGUAGE);

        if(validator.isLocaleExists(language)){
            session.setAttribute(LOCALE, language);
        } else {
            logger.warn("incorrect locale parameter: " + language);
        }
        router.setPagePath(currentPage);
        return router;
    }
}
