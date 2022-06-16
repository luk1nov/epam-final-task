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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.lukyanov.finaltask.command.Message.*;
import static by.lukyanov.finaltask.command.PagePath.*;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;


public class UpdateUserInfoCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(USER_ACCOUNT);
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute(LOGGED_USER);
        String email = request.getParameter(USER_EMAIL);
        String phone = request.getParameter(USER_PHONE);
        Map<String, String> userData = requestAttrToMap(request);
        try {
            if (loggedUser != null){
                long userId = loggedUser.getId();
                if (checkDuplicateByPhone(phone, userId) || checkDuplicateByEmail(email, userId)){
                    request.setAttribute(MESSAGE, USER_EXISTS);
                } else if (userService.updateUserInfo(userId, userData)){
                    Optional<User> optionalUser = userService.findUserById(String.valueOf(userId));
                    if (optionalUser.isPresent()){
                        loggedUser = optionalUser.get();
                        session.setAttribute(LOGGED_USER, loggedUser);
                        router.setType(Router.Type.REDIRECT);
                        router.setPagePath(generateUrlWithAttr(TO_GO_USER_ACCOUNT, MESSAGE_ATTR, INFO_UPDATED));
                    }
                } else {
                    request.setAttribute(MESSAGE, INFO_NOT_UPDATED);
                }
            } else {
                router.setPagePath(TO_LOG_OUT);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying update user info", e);
            throw new CommandException(e);
        }
        return router;
    }

    private Map<String, String> requestAttrToMap(HttpServletRequest request){
        Map<String, String> userData = new HashMap<>();
        userData.put(USER_NAME, request.getParameter(USER_NAME));
        userData.put(USER_SURNAME, request.getParameter(USER_SURNAME));
        userData.put(USER_EMAIL, request.getParameter(USER_EMAIL));
        userData.put(USER_PHONE, request.getParameter(USER_PHONE));
        return userData;
    }

    private boolean checkDuplicateByPhone(String phone, Long userId) throws ServiceException {
        boolean isDuplicate = false;
        Optional<User> optionalUser = userService.findUserByPhone(phone);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            isDuplicate = userId.compareTo(user.getId()) != 0;
        }
        return isDuplicate;
    }

    private boolean checkDuplicateByEmail(String email, Long userId) throws ServiceException {
        boolean isDuplicate = false;
        Optional<User> optionalUser = userService.findUserByEmail(email);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            isDuplicate = userId.compareTo(user.getId()) != 0;
        }
        return isDuplicate;
    }
}
