package by.lukyanov.finaltask.command.impl.login;

import by.lukyanov.finaltask.command.*;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class SignInCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String email = request.getParameter(ParameterAndAttribute.USER_EMAIL).strip();
        String password = request.getParameter(ParameterAndAttribute.USER_PASS);
        Optional<User> optionalUser;
        User user;
        HttpSession session = request.getSession();
        try {
            optionalUser = userService.authenticate(email, password);
            if (optionalUser.isPresent()){
                user = optionalUser.get();
                session.setAttribute(ParameterAndAttribute.LOGGED_USER, user);
                router.setType(Router.Type.REDIRECT);
                router.setPagePath(PagePath.MAIN_PAGE);
            } else {
                router.setPagePath(PagePath.SIGNIN_PAGE);
                request.setAttribute(ParameterAndAttribute.MESSAGE, Message.INCORRECT_EMAIL_OR_LOGIN);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying authenticate user by email & pass", e);
            throw new CommandException(e);
        }
        return router;
    }
}
