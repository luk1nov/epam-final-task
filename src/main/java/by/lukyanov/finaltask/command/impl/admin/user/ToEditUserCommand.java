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
import static by.lukyanov.finaltask.command.PagePath.ADMIN_ADD_EDIT_USER;
import static by.lukyanov.finaltask.command.PagePath.TO_ADMIN_EDIT_USER;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class ToEditUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Router router = new Router(ADMIN_ADD_EDIT_USER);
        String userId = request.getParameter(USER_ID);
        try {
            Optional<User> optionalUser = userService.findUserById(userId);
            if (optionalUser.isPresent()){
                request.setAttribute(USER, optionalUser.get());
                session.setAttribute(CURRENT_PAGE, generateUrlWithAttr(TO_ADMIN_EDIT_USER, USER_ID_ATTR, userId));
            } else{
                router.setPagePath(generateUrlWithAttr(currentPage, MESSAGE_ATTR, USER_NOT_EXISTS));
                router.setType(Router.Type.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying find user by id");
            throw new CommandException(e);
        }
        return router;
    }
}
