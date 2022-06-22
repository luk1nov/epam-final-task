package by.lukyanov.finaltask.command.impl.common.login;

import by.lukyanov.finaltask.command.Command;
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

import static by.lukyanov.finaltask.command.Message.INCORRECT_EMAIL_OR_PASS;
import static by.lukyanov.finaltask.command.Message.USER_NOT_EXISTS;
import static by.lukyanov.finaltask.command.PagePath.MAIN_PAGE;
import static by.lukyanov.finaltask.command.PagePath.SIGN_IN_PAGE;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class SignInCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();
    private static final PasswordEncoder passwordEncoder = PasswordEncoder.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(SIGN_IN_PAGE);
        String email = request.getParameter(USER_EMAIL);
        String password = request.getParameter(USER_PASS);
        HttpSession session = request.getSession();
        try {
            Optional<User> optionalUser = userService.findUserByEmail(email);
            if (optionalUser.isPresent()){
                User user = optionalUser.get();
                if(passwordEncoder.verify(user.getPassword(), password)){
                    session.setAttribute(LOGGED_USER, user);
                    router.setType(Router.Type.REDIRECT);
                    router.setPagePath(MAIN_PAGE);
                } else {
                    request.setAttribute(MESSAGE, INCORRECT_EMAIL_OR_PASS);
                    request.setAttribute(USER_EMAIL, email);
                }
            } else {
                request.setAttribute(MESSAGE, USER_NOT_EXISTS);
                request.setAttribute(USER_EMAIL, email);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying authenticate user by email & pass", e);
            throw new CommandException(e);
        }
        return router;
    }
}
