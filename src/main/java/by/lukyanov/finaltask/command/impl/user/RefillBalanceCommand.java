package by.lukyanov.finaltask.command.impl.user;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.ParameterAndAttribute;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.lukyanov.finaltask.command.PagePath.*;
import static by.lukyanov.finaltask.command.ParameterAndAttribute.*;

public class RefillBalanceCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute(LOGGED_USER);
        String amount = request.getParameter(REFILL_AMOUNT);
        try {
            if (loggedUser != null){
                if(userService.refillBalance(loggedUser.getId(), amount)){
                    router.setPagePath(SUCCESSFUL_REFILL_BALANCE);
                    router.setType(Router.Type.REDIRECT);
                } else {
                    router.setPagePath(FAIL_REFILL_BALANCE);
                }
            } else {
                request.setAttribute(MESSAGE, "<script>alert('123');</script>");
                router.setPagePath(FAIL_REFILL_BALANCE);
            }
        } catch (ServiceException e) {
            logger.error("Service exception trying refill balance", e);
            throw new CommandException(e);
        }
        return router;
    }
}
