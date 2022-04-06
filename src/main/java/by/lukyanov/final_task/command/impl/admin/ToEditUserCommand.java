package by.lukyanov.final_task.command.impl.admin;

import by.lukyanov.final_task.command.*;
import by.lukyanov.final_task.entity.User;
import by.lukyanov.final_task.exception.CommandException;
import by.lukyanov.final_task.exception.ServiceException;
import by.lukyanov.final_task.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class ToEditUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String userId = request.getParameter(ParameterAndAttribute.USER_ID).strip();
        logger.debug("request id " + userId);
        try {
            Optional<User> optionalUser = userService.findUserById(userId);
            if (optionalUser.isPresent()){
                User user = optionalUser.get();
                logger.debug("user found " + user.getId());
                router.setPagePath(PagePath.ADMIN_EDIT_USER);
                request.setAttribute(ParameterAndAttribute.USER, user);
            } else{
                logger.debug("user not found");
                router.setPagePath(PagePath.FAIL_PAGE);
                request.setAttribute(ParameterAndAttribute.MESSAGE, Message.CAN_NOT_EDIT_USER);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying find usr by id");
            throw new CommandException(e);
        }
        return router;
    }
}
