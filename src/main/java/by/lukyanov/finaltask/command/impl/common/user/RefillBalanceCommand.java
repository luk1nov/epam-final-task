package by.lukyanov.finaltask.command.impl.common.user;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.lukyanov.finaltask.command.Message.BALANCE_NOT_REFILLED;
import static by.lukyanov.finaltask.command.Message.BALANCE_REFILLED;
import static by.lukyanov.finaltask.command.PagePath.*;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class RefillBalanceCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(REFILL_BALANCE);
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute(LOGGED_USER);
        String amount = request.getParameter(REFILL_AMOUNT);
        try {
            if (loggedUser != null){
                if(userService.refillBalance(loggedUser.getId(), amount)){
                    router.setPagePath(generateUrlWithAttr(TO_GO_USER_ACCOUNT, MESSAGE_ATTR, BALANCE_REFILLED));
                    router.setType(Router.Type.REDIRECT);
                } else {
                    request.setAttribute(MESSAGE, BALANCE_NOT_REFILLED);
                }
            } else {
                router.setPagePath(TO_LOG_OUT);
            }
        } catch (ServiceException e) {
            logger.error("Service exception trying refill balance", e);
            throw new CommandException(e);
        }
        return router;
    }
}
