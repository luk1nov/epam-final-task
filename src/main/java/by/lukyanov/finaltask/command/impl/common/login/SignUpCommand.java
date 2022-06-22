package by.lukyanov.finaltask.command.impl.common.login;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Message;
import by.lukyanov.finaltask.command.ParameterAttributeName;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.UserServiceImpl;
import by.lukyanov.finaltask.util.mail.MailSender;
import by.lukyanov.finaltask.util.mail.MailType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static by.lukyanov.finaltask.command.Message.*;
import static by.lukyanov.finaltask.command.PagePath.SIGN_UP_PAGE;
import static by.lukyanov.finaltask.command.PagePath.TO_SIGN_IN;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class SignUpCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
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
                        var currentLocale = (String) session.getAttribute(LOCALE);
                        router.setPagePath(generateUrlWithAttr(TO_SIGN_IN, MESSAGE_ATTR, USER_ADDED));
                        var sender = MailSender.getInstance();
                        sender.send(MailType.SIGN_UP, email, currentLocale);
                    } else {
                        request.setAttribute(USER, userData);
                        request.setAttribute(MESSAGE, USER_NOT_ADDED);
                    }
                } else {
                    request.setAttribute(USER, userData);
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
}
