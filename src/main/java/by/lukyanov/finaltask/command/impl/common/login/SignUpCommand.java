package by.lukyanov.finaltask.command.impl.common.login;

import by.lukyanov.finaltask.command.*;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static by.lukyanov.finaltask.command.Message.*;
import static by.lukyanov.finaltask.command.PagePath.*;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class SignUpCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(SIGN_UP_PAGE);
        String email = request.getParameter(USER_EMAIL);
        String phone = request.getParameter(USER_PHONE);
        String password = request.getParameter(USER_PASS);
        String repeatedPassword = request.getParameter(USER_REPEAT_PASS);
        Map<String, String> userData = requestAttrToUserData(request);
        if(repeatedPassword.equals(password)){
            try {
                if(userService.findUserByEmail(email).isEmpty() && userService.findUserByPhone(phone).isEmpty()) {
                    if (userService.addUser(userData)) {
                        router.setType(Router.Type.REDIRECT);
                        router.setPagePath(generateUrlWithAttr(TO_SIGN_IN, MESSAGE_ATTR, USER_ADDED));
                    } else {
                        forwardUserData(request);
                        request.setAttribute(MESSAGE, USER_NOT_ADDED);
                    }
                } else {
                    forwardUserData(request);
                    request.setAttribute(MESSAGE, USER_EXISTS);
                }
            } catch (ServiceException e) {
                logger.error("Command exception trying find user by email", e);
                throw new CommandException(e);
            }
        } else{
            router.setPagePath(SIGN_UP_PAGE);
            request.setAttribute(ParameterAttributeName.MESSAGE, Message.PASSWORD_MISMATCH);
        }
        return router;
    }

    private Map<String, String> requestAttrToUserData(HttpServletRequest request){
        Map<String, String> userData = new HashMap<>();
        userData.put(USER_NAME, request.getParameter(USER_NAME));
        userData.put(USER_SURNAME, request.getParameter(USER_SURNAME));
        userData.put(USER_EMAIL, request.getParameter(USER_EMAIL));
        userData.put(USER_PHONE, request.getParameter(USER_PHONE));
        userData.put(USER_PASS, request.getParameter(USER_PASS));
        return userData;
    }

    private void forwardUserData(HttpServletRequest request){
        request.setAttribute(USER_NAME, request.getParameter(USER_NAME));
        request.setAttribute(USER_SURNAME, request.getParameter(USER_SURNAME));
        request.setAttribute(USER_EMAIL, request.getParameter(USER_EMAIL));
        request.setAttribute(USER_PHONE, request.getParameter(USER_PHONE));
    }
}
