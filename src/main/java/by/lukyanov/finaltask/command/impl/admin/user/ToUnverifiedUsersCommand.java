package by.lukyanov.finaltask.command.impl.admin.user;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.ParameterAttributeName;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.entity.UserStatus;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.lukyanov.finaltask.command.PagePath.UNVERIFIED_USERS;

public class ToUnverifiedUsersCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(UNVERIFIED_USERS);
        try {
            List<User> unverifiedUsers = userService.findUsersByStatus(UserStatus.VERIFICATION);
            request.setAttribute(ParameterAttributeName.LIST, unverifiedUsers);
        } catch (ServiceException e) {
            logger.error("Command exception trying find unverified users", e);
            throw new CommandException(e);
        }
        return router;
    }
}
