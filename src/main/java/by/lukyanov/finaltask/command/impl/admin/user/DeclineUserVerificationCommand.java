package by.lukyanov.finaltask.command.impl.admin.user;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.ParameterAttributeName;
import by.lukyanov.finaltask.command.Router;
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

import static by.lukyanov.finaltask.command.Message.USER_DECLINED;
import static by.lukyanov.finaltask.command.Message.USER_NOT_DECLINED;
import static by.lukyanov.finaltask.command.PagePath.TO_ADMIN_UNVERIFIED_USERS;
import static by.lukyanov.finaltask.command.ParameterAttributeName.LOCALE;
import static by.lukyanov.finaltask.command.ParameterAttributeName.MESSAGE_ATTR;

public class DeclineUserVerificationCommand implements Command {
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
            if (user.isPresent() && userService.updateUserStatus(userId, UserStatus.INACTIVE)){
                router.setPagePath(generateUrlWithAttr(TO_ADMIN_UNVERIFIED_USERS, MESSAGE_ATTR, USER_DECLINED));
                MailSender sender = MailSender.getInstance();
                sender.send(MailType.DECLINE_ACCOUNT, user.get().getEmail(), currentLocale);
            } else {
                router.setPagePath(generateUrlWithAttr(TO_ADMIN_UNVERIFIED_USERS, MESSAGE_ATTR, USER_NOT_DECLINED));
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying decline user verification", e);
            throw new CommandException(e);
        }
        return router;
    }
}
