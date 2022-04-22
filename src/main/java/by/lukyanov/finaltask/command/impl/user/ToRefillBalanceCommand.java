package by.lukyanov.finaltask.command.impl.user;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import static by.lukyanov.finaltask.command.PagePath.REFILL_BALANCE;


public class ToRefillBalanceCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return new Router(REFILL_BALANCE);
    }
}
