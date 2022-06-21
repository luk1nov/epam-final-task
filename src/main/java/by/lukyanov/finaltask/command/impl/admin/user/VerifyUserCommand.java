package by.lukyanov.finaltask.command.impl.admin.user;

import by.lukyanov.finaltask.command.*;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.entity.UserStatus;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.UserServiceImpl;
import by.lukyanov.finaltask.util.mail.MailSender;
import by.lukyanov.finaltask.util.mail.MailType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.lukyanov.finaltask.command.Message.USER_NOT_VERIFIED;
import static by.lukyanov.finaltask.command.Message.USER_VERIFIED;
import static by.lukyanov.finaltask.command.PagePath.TO_ADMIN_UNVERIFIED_USERS;
import static by.lukyanov.finaltask.command.ParameterAttributeName.LOCALE;
import static by.lukyanov.finaltask.command.ParameterAttributeName.MESSAGE_ATTR;

public class VerifyUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentLocale = (String) session.getAttribute(LOCALE);
        String userId = request.getParameter(ParameterAttributeName.USER_ID);
        Router router = new Router(Router.Type.REDIRECT, TO_ADMIN_UNVERIFIED_USERS);
        try {
            Optional<User> user = userService.findUserById(userId);
            if (user.isPresent() && userService.updateUserStatus(userId, UserStatus.ACTIVE)){
                router.setPagePath(generateUrlWithAttr(TO_ADMIN_UNVERIFIED_USERS, MESSAGE_ATTR, USER_VERIFIED));
                MailSender sender = MailSender.getInstance();
                sender.send(MailType.CONFIRM_ACCOUNT, user.get().getEmail(), currentLocale);
            } else {
                router.setPagePath(generateUrlWithAttr(TO_ADMIN_UNVERIFIED_USERS, MESSAGE_ATTR, USER_NOT_VERIFIED));
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying verify user", e);
            throw new CommandException(e);
        }
        return router;
    }
}
