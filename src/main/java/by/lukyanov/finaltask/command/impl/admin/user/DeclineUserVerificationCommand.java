package by.lukyanov.finaltask.command.impl.admin.user;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.ParameterAttributeName;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.UserStatus;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.lukyanov.finaltask.command.Message.USER_DECLINED;
import static by.lukyanov.finaltask.command.Message.USER_NOT_DECLINED;
import static by.lukyanov.finaltask.command.PagePath.TO_ADMIN_UNVERIFIED_USERS;
import static by.lukyanov.finaltask.command.ParameterAttributeName.MESSAGE_ATTR;

public class DeclineUserVerificationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String userId = request.getParameter(ParameterAttributeName.USER_ID);
        Router router = new Router(Router.Type.REDIRECT, TO_ADMIN_UNVERIFIED_USERS);
        try {
            if (userService.updateUserStatus(userId, UserStatus.INACTIVE)){
                router.setPagePath(generateUrlWithAttr(TO_ADMIN_UNVERIFIED_USERS, MESSAGE_ATTR, USER_DECLINED));
            } else {
                router.setPagePath(generateUrlWithAttr(TO_ADMIN_UNVERIFIED_USERS, MESSAGE_ATTR, USER_NOT_DECLINED));
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying decline user verification", e);
            throw new CommandException(e);
        }
        return router;
    }
}
