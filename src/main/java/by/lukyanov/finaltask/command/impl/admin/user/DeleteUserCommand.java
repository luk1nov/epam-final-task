package by.lukyanov.finaltask.command.impl.admin.user;

import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.UserServiceImpl;
import by.lukyanov.finaltask.command.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String userId = request.getParameter(ParameterAndAttribute.USER_ID).strip();
        HttpSession session = request.getSession(false);
        User loggedUser = (User) session.getAttribute(ParameterAndAttribute.LOGGED_USER);
        boolean result = false;
        if(loggedUser.getId() != Long.parseLong(userId)){
            try {
                result = userService.deleteUser(userId);
            } catch (ServiceException e) {
                logger.error("Command exception trying delete user");
                throw new CommandException(e);
            }
        }
        if(result){
            router.setPagePath(PagePath.ADMIN_SUCCESS_PAGE);
            router.setType(Router.Type.REDIRECT);
        } else {
            request.setAttribute(ParameterAndAttribute.MESSAGE, Message.USER_NOT_DELETED);
            router.setPagePath(PagePath.ADMIN_FAIL_PAGE);
        }
        return router;
    }
}
