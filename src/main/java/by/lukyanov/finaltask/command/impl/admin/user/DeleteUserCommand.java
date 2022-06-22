package by.lukyanov.finaltask.command.impl.admin.user;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.ParameterAttributeName;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.lukyanov.finaltask.command.Message.USER_DELETED;
import static by.lukyanov.finaltask.command.Message.USER_NOT_DELETED;
import static by.lukyanov.finaltask.command.ParameterAttributeName.CURRENT_PAGE;
import static by.lukyanov.finaltask.command.ParameterAttributeName.MESSAGE_ATTR;

public class DeleteUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        User loggedUser = (User) session.getAttribute(ParameterAttributeName.LOGGED_USER);
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);
        String userId = request.getParameter(ParameterAttributeName.USER_ID);
        try {
            if(userService.deleteUser(userId, loggedUser.getId())){
                router.setPagePath(generateUrlWithAttr(currentPage, MESSAGE_ATTR, USER_DELETED));
            } else {
                router.setPagePath(generateUrlWithAttr(currentPage, MESSAGE_ATTR, USER_NOT_DELETED));
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying delete user");
            throw new CommandException(e);
        }
        return router;
    }
}
