package by.lukyanov.finaltask.command.impl.admin.user;

import by.lukyanov.finaltask.command.*;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class ToEditUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = new UserServiceImpl();
    private static final String PHONE_CODE_BY = "\\+375-";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String userId = request.getParameter(ParameterAttributeName.USER_ID).strip();
        logger.debug("request id " + userId);
        try {
            Optional<User> optionalUser = userService.findUserById(userId);
            if (optionalUser.isPresent()){
                User user = optionalUser.get();
                logger.debug("user found " + user.getId());
                String phoneWithoutCode = user.getPhone().replaceAll(PHONE_CODE_BY, "");
                user.setPhone(phoneWithoutCode);
                router.setPagePath(PagePath.ADMIN_ADD_EDIT_USER);
                request.setAttribute(ParameterAttributeName.USER, user);
            } else{
                logger.debug("user not found");
                router.setPagePath(PagePath.ADMIN_FAIL_PAGE);
                request.setAttribute(ParameterAttributeName.MESSAGE, Message.CAN_NOT_EDIT_USER);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying find usr by id");
            throw new CommandException(e);
        }
        return router;
    }
}
