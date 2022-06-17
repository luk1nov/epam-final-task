package by.lukyanov.finaltask.command.impl.admin.user;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.lukyanov.finaltask.command.Message.USER_NOT_EXISTS;
import static by.lukyanov.finaltask.command.PagePath.ADMIN_USER_INFO;
import static by.lukyanov.finaltask.command.PagePath.TO_ADMIN_TO_USER_INFO;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class ToUserInfoCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Router router = new Router();
        String userId = request.getParameter(USER_ID);
        try {
            Optional<User> optionalUser = userService.findUserById(userId);
            if(optionalUser.isPresent()){
                session.setAttribute(CURRENT_PAGE, generateUrlWithAttr(TO_ADMIN_TO_USER_INFO, USER_ID_ATTR, userId));
                router.setPagePath(ADMIN_USER_INFO);
                request.setAttribute(USER, optionalUser.get());
            } else {
                router.setType(Router.Type.REDIRECT);
                router.setPagePath(generateUrlWithAttr(currentPage, MESSAGE_ATTR, USER_NOT_EXISTS));
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying find user info", e);
            throw new CommandException(e);
        }
        return router;
    }
}
