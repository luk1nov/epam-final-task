package by.lukyanov.finaltask.command.impl.common.user;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static by.lukyanov.finaltask.command.Message.*;
import static by.lukyanov.finaltask.command.PagePath.TO_GO_USER_ACCOUNT;
import static by.lukyanov.finaltask.command.PagePath.TO_LOG_OUT;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class UpdateDriverLicenseCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute(LOGGED_USER);
        try (InputStream is = request.getPart(USER_DRIVER_LICENSE).getInputStream()){
            if (loggedUser != null){
                long userId = loggedUser.getId();
                if(userService.updateDriverLicense(userId, is)){
                    Optional<User> optionalUser = userService.findUserById(String.valueOf(userId));
                    if (optionalUser.isPresent()){
                        loggedUser = optionalUser.get();
                        session.setAttribute(LOGGED_USER, loggedUser);
                        router.setPagePath(generateUrlWithAttr(TO_GO_USER_ACCOUNT, MESSAGE_ATTR, DRIVER_LICENSE_UPDATED));
                    } else {
                        router.setPagePath(TO_LOG_OUT);
                    }
                } else {
                    router.setPagePath(generateUrlWithAttr(TO_GO_USER_ACCOUNT, MESSAGE_ATTR, DRIVER_LICENSE_NOT_UPDATED));
                }
            }
        } catch (ServiceException | IOException | ServletException e) {
            logger.error("Command exception trying update driver license and set verification status", e);
            throw new CommandException(e);
        }
        return router;
    }
}
