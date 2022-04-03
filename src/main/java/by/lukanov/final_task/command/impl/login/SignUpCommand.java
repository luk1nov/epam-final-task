package by.lukanov.final_task.command.impl.login;

import by.lukanov.final_task.command.*;
import by.lukanov.final_task.exception.CommandException;
import by.lukanov.final_task.exception.ServiceException;
import by.lukanov.final_task.service.impl.UserServiceImpl;
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
        String email = request.getParameter(ParameterAndAttribute.REGISTRATION_EMAIL.getAttr()).strip();
        String password = request.getParameter(ParameterAndAttribute.REGISTRATION_PASSWORD.getAttr());
        String repeatedPassword = request.getParameter(ParameterAndAttribute.REGISTRATION_REPEATED_PASSWORD.getAttr());
        String name = request.getParameter(ParameterAndAttribute.REGISTRATION_NAME.getAttr()).strip();
        String surname = request.getParameter(ParameterAndAttribute.REGISTRATION_SURNAME.getAttr()).strip();

        Map<String, String> userData = new HashMap<>();
        userData.put(ParameterAndAttribute.USER_NAME.getAttr(), name);
        userData.put(ParameterAndAttribute.USER_SURNAME.getAttr(), surname);
        userData.put(ParameterAndAttribute.USER_EMAIL.getAttr(), email);
        userData.put(ParameterAndAttribute.USER_PASS.getAttr(), password);
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
                        request.setAttribute(ParameterAndAttribute.MESSAGE.getAttr(), Message.USER_NOT_ADDED);
                    }
                } else {
                    logger.info("user already exists with such email address");
                    router.setPagePath(PagePath.SIGNUP_PAGE);
                    request.setAttribute(ParameterAndAttribute.MESSAGE.getAttr(), Message.USER_EXISTS);
                }
            } catch (ServiceException e) {
                logger.error("Command exception trying find user by email", e);
                throw new CommandException(e);
            }
        } else{
            router.setPagePath(PagePath.SIGNUP_PAGE);
            request.setAttribute(ParameterAndAttribute.MESSAGE.getAttr(), Message.PASSWORD_MISMATCH);
        }
        return router;
    }
}
