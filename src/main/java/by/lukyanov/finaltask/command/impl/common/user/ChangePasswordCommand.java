package by.lukyanov.finaltask.command.impl.common.user;

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

import static by.lukyanov.finaltask.command.Message.*;
import static by.lukyanov.finaltask.command.PagePath.*;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class ChangePasswordCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();
    private static final PasswordEncoder passwordEncoder = PasswordEncoder.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(PagePath.USER_ACCOUNT);
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute(LOGGED_USER);
        long userId = loggedUser.getId();
        String expectedCurrentPassword = request.getParameter(CURRENT_PASSWORD);
        String newPassword = request.getParameter(NEW_PASSWORD);
        String repeatedNewPassword = request.getParameter(REPEATED_NEW_PASSWORD);
        router.setType(Router.Type.REDIRECT);
        try {
            Optional<User> optionalUser = userService.findUserByEmail(loggedUser.getEmail());
            if (optionalUser.isPresent()){
                if (!repeatedNewPassword.equals(newPassword)){
                    router.setPagePath(generateUrlWithAttr(TO_GO_USER_ACCOUNT, MESSAGE_ATTR, PASSWORD_MISMATCH));
                } else if (!passwordEncoder.verify(optionalUser.get().getPassword(), expectedCurrentPassword)){
                    router.setPagePath(generateUrlWithAttr(TO_GO_USER_ACCOUNT, MESSAGE_ATTR, INCORRECT_CURRENT_PASS));
                } else if (userService.changeUserPassword(userId, newPassword)){
                    router.setPagePath(generateUrlWithAttr(TO_LOG_OUT, MESSAGE_ATTR, PASSWORD_CHANGED));
                } else {
                    router.setPagePath(generateUrlWithAttr(TO_GO_USER_ACCOUNT, MESSAGE_ATTR, PASSWORD_NOT_CHANGED));
                }
            } else {
                router.setPagePath(TO_LOG_OUT);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying change password", e);
            throw new CommandException(e);
        }
        return router;
    }
}
