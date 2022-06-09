package by.lukyanov.finaltask.command.impl.admin.user;

import by.lukyanov.finaltask.command.*;
import by.lukyanov.finaltask.entity.UserStatus;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.lukyanov.finaltask.command.Message.USER_NOT_VERIFIED;
import static by.lukyanov.finaltask.command.Message.USER_VERIFIED;
import static by.lukyanov.finaltask.command.ParameterAttributeName.MESSAGE;
import static by.lukyanov.finaltask.command.ParameterAttributeName.MESSAGE_ATTR;

public class VerifyUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String userId = request.getParameter(ParameterAttributeName.USER_ID);
        Router router = new Router(PagePath.TO_UNVERIFIED_USERS);
        try {
            if (userService.updateUserStatus(userId, UserStatus.ACTIVE)){
                String path = PagePath.TO_UNVERIFIED_USERS + MESSAGE_ATTR + USER_VERIFIED;
                router.setPagePath(path);
                router.setType(Router.Type.REDIRECT);
            } else {
                request.setAttribute(MESSAGE, USER_NOT_VERIFIED);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying verify user", e);
            throw new CommandException(e);
        }
        return router;
    }
}
