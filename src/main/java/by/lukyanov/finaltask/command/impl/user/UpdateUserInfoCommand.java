package by.lukyanov.finaltask.command.impl.user;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.PagePath;
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

import static by.lukyanov.finaltask.command.Message.INFO_NOT_UPDATED;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;


public class UpdateUserInfoCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(PagePath.MAIN_PAGE);
        Map<String, String> userData = new HashMap<>();
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute(LOGGED_USER);

        String name = request.getParameter(USER_NAME);
        String surname = request.getParameter(USER_SURNAME);
        String email = request.getParameter(USER_EMAIL);
        String phone = request.getParameter(USER_PHONE);

        userData.put(USER_NAME, name);
        userData.put(USER_SURNAME, surname);
        userData.put(USER_EMAIL, email);
        userData.put(USER_PHONE, phone);
        try {
            if (loggedUser != null){
                long userId = loggedUser.getId();
                if (userService.updateUserInfo(userId, userData)){
                    Optional<User> optionalUser = userService.findUserById(String.valueOf(userId));
                    if (optionalUser.isPresent()){
                        loggedUser = optionalUser.get();
                        session.setAttribute(LOGGED_USER, loggedUser);
                        router.setType(Router.Type.REDIRECT);
                        router.setPagePath(PagePath.USER_ACCOUNT);
                    } else {
                        session.removeAttribute(LOGGED_USER);
                    }
                } else {
                    request.setAttribute(MESSAGE, INFO_NOT_UPDATED);
                    router.setPagePath(PagePath.USER_ACCOUNT);
                }
            } else {
                session.removeAttribute(LOGGED_USER);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying update user info", e);
            throw new CommandException(e);
        }
        return router;
    }
}
