package by.lukyanov.finaltask.command.impl.user;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.UserServiceImpl;
import by.lukyanov.finaltask.util.PasswordEncoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.lukyanov.finaltask.command.PagePath.MAIN_PAGE;
import static by.lukyanov.finaltask.command.PagePath.SIGNIN_PAGE;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class ChangePasswordCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(PagePath.USER_ACCOUNT);
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute(LOGGED_USER);
        long userId = loggedUser.getId();
        String expectedCurrentPassword = request.getParameter(CURRENT_PASSWORD);
        String newPassword = request.getParameter(NEW_PASSWORD);
        String repeatedNewPassword = request.getParameter(REPEATED_NEW_PASSWORD);
        if (repeatedNewPassword.equals(newPassword)){
            try {
                Optional<User> optionalUser = userService.findUserByEmail(loggedUser.getEmail());
                if (optionalUser.isPresent()){
                    if (PasswordEncoder.getInstance().verify(optionalUser.get().getPassword(), expectedCurrentPassword) && userService.changeUserPassword(userId, newPassword)){
                        router.setPagePath(SIGNIN_PAGE);
                        router.setType(Router.Type.REDIRECT);
                        session.removeAttribute(LOGGED_USER);
                    } else {
                        logger.warn("password mismatch or doesnt changed");
                    }
                } else {
                    logger.warn("user not found");
                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
        return router;
    }
}
