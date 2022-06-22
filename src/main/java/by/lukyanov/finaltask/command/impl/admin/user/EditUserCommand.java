package by.lukyanov.finaltask.command.impl.admin.user;

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
import static by.lukyanov.finaltask.command.PagePath.TO_ADMIN_ALL_USERS;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class EditUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Router router = new Router(currentPage);
        Map<String, String> userData = requestAttrToUserData(request);
        String userId = request.getParameter(USER_ID);
        String phone = request.getParameter(USER_PHONE);
        String email = request.getParameter(USER_EMAIL);
        try {
            if(checkDuplicateByEmail(email, userId) || checkDuplicateByPhone(phone, userId)){
                request.setAttribute(MESSAGE, USER_EXISTS);
            } else if(!userService.updateUser(userData)){
                request.setAttribute(MESSAGE, USER_NOT_EDITED);
            } else {
                router.setType(Router.Type.REDIRECT);
                router.setPagePath(generateUrlWithAttr(TO_ADMIN_ALL_USERS, MESSAGE_ATTR, USER_EDITED));
            }
        } catch (ServiceException e) {
            logger.error("Service exception trying update user");
            throw new CommandException(e);
        }
        return router;
    }

    private Map<String, String> requestAttrToUserData(HttpServletRequest request){
        Map<String, String> userData = new HashMap<>();
        userData.put(USER_ID, request.getParameter(USER_ID));
        userData.put(USER_NAME, request.getParameter(USER_NAME));
        userData.put(USER_SURNAME, request.getParameter(USER_SURNAME));
        userData.put(USER_EMAIL, request.getParameter(USER_EMAIL));
        userData.put(USER_PHONE, request.getParameter(USER_PHONE));
        userData.put(USER_STATUS, request.getParameter(USER_STATUS));
        userData.put(USER_ROLE, request.getParameter(USER_ROLE));
        return userData;
    }

    private boolean checkDuplicateByPhone(String phone, String userId) throws ServiceException {
        boolean isDuplicate = false;
        Optional<User> optionalUser = userService.findUserByPhone(phone);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            isDuplicate = !userId.equals(String.valueOf(user.getId()));
        }
        return isDuplicate;
    }

    private boolean checkDuplicateByEmail(String email, String userId) throws ServiceException {
        boolean isDuplicate = false;
        Optional<User> optionalUser = userService.findUserByEmail(email);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            isDuplicate = !userId.equals(String.valueOf(user.getId()));
        }
        return isDuplicate;
    }
}
