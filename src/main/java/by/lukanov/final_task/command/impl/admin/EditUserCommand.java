package by.lukanov.final_task.command.impl.admin;

import by.lukanov.final_task.command.*;
import by.lukanov.final_task.exception.CommandException;
import by.lukanov.final_task.exception.ServiceException;
import by.lukanov.final_task.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class EditUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        boolean updated;
        Router router = new Router();
        String userId = request.getParameter(ParameterAndAttribute.USER_ID.getAttr());
        String name = request.getParameter(ParameterAndAttribute.USER_NAME.getAttr());
        String surname = request.getParameter(ParameterAndAttribute.USER_SURNAME.getAttr());
        String email = request.getParameter(ParameterAndAttribute.USER_EMAIL.getAttr());
        String role = request.getParameter(ParameterAndAttribute.USER_ROLE.getAttr());
        String status = request.getParameter(ParameterAndAttribute.USER_STATUS.getAttr());

        Map<String, String> userData = new HashMap<>();
        userData.put(ParameterAndAttribute.USER_ID.getAttr(), userId);
        userData.put(ParameterAndAttribute.USER_NAME.getAttr(), name);
        userData.put(ParameterAndAttribute.USER_SURNAME.getAttr(), surname);
        userData.put(ParameterAndAttribute.USER_EMAIL.getAttr(), email);
        userData.put(ParameterAndAttribute.USER_STATUS.getAttr(), status);
        userData.put(ParameterAndAttribute.USER_ROLE.getAttr(), role);

        try {
            updated = userService.updateUser(userData);
        } catch (ServiceException e) {
            logger.error("Service exception trying update user");
            throw new CommandException(e);
        }
        if (updated){
            router.setPagePath(PagePath.SUCCESS_PAGE);
        } else {
            router.setPagePath(PagePath.FAIL_PAGE);
        }
        router.setType(Router.Type.REDIRECT);
        return router;
    }
}
