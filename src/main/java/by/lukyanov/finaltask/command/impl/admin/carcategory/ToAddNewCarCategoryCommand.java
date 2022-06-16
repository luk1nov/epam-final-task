package by.lukyanov.finaltask.command.impl.admin.carcategory;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.lukyanov.finaltask.command.PagePath.ADMIN_ADD_CAR_CATEGORY;
import static by.lukyanov.finaltask.command.ParameterAttributeName.CURRENT_PAGE;

public class ToAddNewCarCategoryCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.setAttribute(CURRENT_PAGE, ADMIN_ADD_CAR_CATEGORY);
        return new Router(ADMIN_ADD_CAR_CATEGORY);
    }
}
