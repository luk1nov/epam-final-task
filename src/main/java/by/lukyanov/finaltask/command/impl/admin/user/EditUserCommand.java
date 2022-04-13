package by.lukyanov.finaltask.command.impl.admin.user;

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
import static by.lukyanov.finaltask.command.ParameterAndAttribute.*;

public class EditUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        boolean updated;
        Router router = new Router();
        String userId = request.getParameter(USER_ID).strip();
        String name = request.getParameter(USER_NAME).strip();
        String surname = request.getParameter(USER_SURNAME).strip();
        String email = request.getParameter(USER_EMAIL).strip();
        String role = request.getParameter(USER_ROLE).strip();
        String status = request.getParameter(USER_STATUS).strip();

        Map<String, String> userData = new HashMap<>();
        userData.put(USER_ID, userId);
        userData.put(USER_NAME, name);
        userData.put(USER_SURNAME, surname);
        userData.put(USER_EMAIL, email);
        userData.put(USER_STATUS, status);
        userData.put(USER_ROLE, role);

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
