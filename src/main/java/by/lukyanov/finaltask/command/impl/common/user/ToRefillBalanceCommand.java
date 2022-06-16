package by.lukyanov.finaltask.command.impl.common.user;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.lukyanov.finaltask.command.PagePath.REFILL_BALANCE;
import static by.lukyanov.finaltask.command.ParameterAttributeName.CURRENT_PAGE;


public class ToRefillBalanceCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.setAttribute(CURRENT_PAGE, REFILL_BALANCE);
        return new Router(REFILL_BALANCE);
    }
}
