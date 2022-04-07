package by.lukyanov.finaltask.command.impl.admin;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.ParameterAndAttribute;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static by.lukyanov.finaltask.command.Message.*;

public class EditUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        boolean updated;
        Router router = new Router();
        String userId = request.getParameter(ParameterAndAttribute.USER_ID).strip();
        String name = request.getParameter(ParameterAndAttribute.USER_NAME).strip();
        String surname = request.getParameter(ParameterAndAttribute.USER_SURNAME).strip();
        String email = request.getParameter(ParameterAndAttribute.USER_EMAIL).strip();
        String role = request.getParameter(ParameterAndAttribute.USER_ROLE).strip();
        String status = request.getParameter(ParameterAndAttribute.USER_STATUS).strip();

        Map<String, String> userData = new HashMap<>();
        userData.put(ParameterAndAttribute.USER_ID, userId);
        userData.put(ParameterAndAttribute.USER_NAME, name);
        userData.put(ParameterAndAttribute.USER_SURNAME, surname);
        userData.put(ParameterAndAttribute.USER_EMAIL, email);
        userData.put(ParameterAndAttribute.USER_STATUS, status);
        userData.put(ParameterAndAttribute.USER_ROLE, role);

        try {
            updated = userService.updateUser(userData);
        } catch (ServiceException e) {
            logger.error("Service exception trying update user");
            throw new CommandException(e);
        }
        if (updated){
            router.setPagePath(PagePath.SUCCESS_PAGE);
            router.setType(Router.Type.REDIRECT);
        } else {
            request.setAttribute(ParameterAndAttribute.MESSAGE, USER_NOT_UPDATED);
            router.setPagePath(PagePath.FAIL_PAGE);
        }
        return router;
    }
}
