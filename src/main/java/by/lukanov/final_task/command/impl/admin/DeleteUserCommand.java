package by.lukanov.final_task.command.impl.admin;

import by.lukanov.final_task.command.*;
import by.lukanov.final_task.exception.CommandException;
import by.lukanov.final_task.exception.ServiceException;
import by.lukanov.final_task.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String userId = request.getParameter(ParameterAndAttribute.USER_ID.getAttr()).strip();
        boolean result;
        try {
            result = userService.deleteUser(userId);
        } catch (ServiceException e) {
            logger.error("Command exception trying delete user");
            throw new CommandException(e);
        }

        if(result){
            router.setPagePath(PagePath.SUCCESS_PAGE);
            router.setType(Router.Type.REDIRECT);
        } else {
            request.setAttribute(ParameterAndAttribute.MESSAGE.getAttr(), Message.USER_NOT_DELETED);
            router.setPagePath(PagePath.FAIL_PAGE);
        }
        return router;
    }
}
