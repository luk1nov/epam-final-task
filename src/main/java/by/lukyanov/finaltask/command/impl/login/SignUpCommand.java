package by.lukyanov.finaltask.command.impl.login;

import by.lukyanov.finaltask.command.*;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class SignUpCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String email = request.getParameter(ParameterAttributeName.USER_EMAIL);
        String phone = request.getParameter(ParameterAttributeName.USER_PHONE);
        String password = request.getParameter(ParameterAttributeName.USER_PASS);
        String repeatedPassword = request.getParameter(ParameterAttributeName.USER_REPEAT_PASS);
        String name = request.getParameter(ParameterAttributeName.USER_NAME);
        String surname = request.getParameter(ParameterAttributeName.USER_SURNAME);

        Map<String, String> userData = new HashMap<>();
        userData.put(ParameterAttributeName.USER_NAME, name);
        userData.put(ParameterAttributeName.USER_SURNAME, surname);
        userData.put(ParameterAttributeName.USER_EMAIL, email);
        userData.put(ParameterAttributeName.USER_PHONE, phone);
        userData.put(ParameterAttributeName.USER_PASS, password);
        if(repeatedPassword.equals(password)){
            try {
                if(userService.findUserByEmail(email).isEmpty()) {
                    if (userService.addUser(userData)) {
                        logger.info("user added");
                        router.setType(Router.Type.REDIRECT);
                        router.setPagePath(PagePath.SIGNIN_PAGE);
                    } else {
                        logger.info("user not added");
                        router.setPagePath(PagePath.SIGNUP_PAGE);
                        request.setAttribute(ParameterAttributeName.MESSAGE, Message.USER_NOT_ADDED);
                    }
                } else {
                    logger.info("user already exists with such email address");
                    router.setPagePath(PagePath.SIGNUP_PAGE);
                    request.setAttribute(ParameterAttributeName.MESSAGE, Message.USER_EXISTS);
                }
            } catch (ServiceException e) {
                logger.error("Command exception trying find user by email", e);
                throw new CommandException(e);
            }
        } else{
            router.setPagePath(PagePath.SIGNUP_PAGE);
            request.setAttribute(ParameterAttributeName.MESSAGE, Message.PASSWORD_MISMATCH);
        }
        return router;
    }
}
