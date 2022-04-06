package by.lukyanov.final_task.command.impl.admin;

import by.lukyanov.final_task.command.Command;
import by.lukyanov.final_task.command.PagePath;
import by.lukyanov.final_task.command.Router;
import by.lukyanov.final_task.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class ToAddNewCarCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setPagePath(PagePath.ADMIN_ADD_NEW_CAR);
        return router;
    }
}
