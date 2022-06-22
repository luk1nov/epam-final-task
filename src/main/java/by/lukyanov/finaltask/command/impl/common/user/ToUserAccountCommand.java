package by.lukyanov.finaltask.command.impl.common.user;

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

import static by.lukyanov.finaltask.command.PagePath.*;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class ToUserAccountCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(TO_LOG_OUT);
        request.setAttribute(MESSAGE, request.getParameter(MESSAGE));
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute(LOGGED_USER);
        try {
            Long userId = loggedUser.getId();
            Optional<User> optionalUser = userService.findUserById(String.valueOf(userId));
            if (optionalUser.isPresent()){
                User user = optionalUser.get();
                session.setAttribute(CURRENT_PAGE, TO_GO_USER_ACCOUNT);
                session.setAttribute(LOGGED_USER, user);
                router.setPagePath(USER_ACCOUNT);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying navigate to user account", e);
            throw new CommandException(e);
        }
        return router;
    }
}
