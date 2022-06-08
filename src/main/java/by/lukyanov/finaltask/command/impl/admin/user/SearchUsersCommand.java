package by.lukyanov.finaltask.command.impl.admin.user;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.lukyanov.finaltask.command.PagePath.*;
import static by.lukyanov.finaltask.command.ParameterAttributeName.LIST;
import static by.lukyanov.finaltask.command.ParameterAttributeName.SEARCH;

public class SearchUsersCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(TO_ADMIN_ALL_USERS);
        String searchQuery = request.getParameter(SEARCH);
        try {
            if(searchQuery != null && !searchQuery.isBlank()){
                router.setPagePath(ADMIN_SEARCH_USERS_RESULTS);
                List<User> users = userService.searchUsers(searchQuery);
                request.setAttribute(LIST, users);
                request.setAttribute(SEARCH, searchQuery.trim());
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying search users", e);
            throw new CommandException(e);
        }
        return router;
    }
}
