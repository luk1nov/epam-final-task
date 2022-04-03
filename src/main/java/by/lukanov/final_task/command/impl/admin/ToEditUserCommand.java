package by.lukanov.final_task.command.impl.admin;

import by.lukanov.final_task.command.*;
import by.lukanov.final_task.entity.User;
import by.lukanov.final_task.exception.CommandException;
import by.lukanov.final_task.exception.ServiceException;
import by.lukanov.final_task.service.impl.UserServiceImpl;
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
        String userId = request.getParameter(ParameterAndAttribute.USER_ID.getAttr());
        Optional<User> optionalUser;
        User user;
        try {
            optionalUser = userService.findUserById(userId);
            if (optionalUser.isPresent()){
                user = optionalUser.get();
                request.setAttribute(ParameterAndAttribute.USER.getAttr(), user);
                request.setAttribute(ParameterAndAttribute.USER_ID.getAttr(), userId);
            } else{
                router.setPagePath(PagePath.ADMIN_ALL_USERS);
                request.setAttribute(ParameterAndAttribute.MESSAGE.getAttr(), Message.CAN_NOT_EDIT_USER);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying find usr by id");
            throw new CommandException(e);
        }
        router.setPagePath(PagePath.ADMIN_EDIT_USER);
        return router;
    }
}
