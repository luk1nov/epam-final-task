package by.lukyanov.finaltask.command.impl.login;

import by.lukyanov.finaltask.command.*;
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

public class SignInCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String email = request.getParameter(ParameterAttributeName.USER_EMAIL).strip();
        String password = request.getParameter(ParameterAttributeName.USER_PASS);
        Optional<User> optionalUser;
        HttpSession session = request.getSession();
        try {
            optionalUser = userService.findUserByEmail(email);
            if (optionalUser.isPresent()){
                User user = optionalUser.get();
                if(PasswordEncoder.getInstance().verify(user.getPassword(), password)){
                    session.setAttribute(ParameterAttributeName.LOGGED_USER, user);
                    router.setType(Router.Type.REDIRECT);
                    router.setPagePath(PagePath.MAIN_PAGE);
                } else {
                    router.setPagePath(PagePath.SIGNIN_PAGE);
                    request.setAttribute(ParameterAttributeName.MESSAGE, Message.INCORRECT_EMAIL_OR_LOGIN);
                }
            } else {
                router.setPagePath(PagePath.SIGNIN_PAGE);
                request.setAttribute(ParameterAttributeName.MESSAGE, "User not exists");
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying authenticate user by email & pass", e);
            throw new CommandException(e);
        }
        return router;
    }
}
