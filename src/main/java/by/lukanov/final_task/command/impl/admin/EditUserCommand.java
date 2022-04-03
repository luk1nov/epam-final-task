package by.lukanov.final_task.command.impl.admin;

import by.lukanov.final_task.command.Command;
import by.lukanov.final_task.command.PagePath;
import by.lukanov.final_task.command.ParameterAndAttribute;
import by.lukanov.final_task.command.Router;
import by.lukanov.final_task.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class EditUserCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String userId = request.getParameter(ParameterAndAttribute.USER_ID.getAttr());



        router.setPagePath(PagePath.ADMIN_ALL_USERS);
        router.setType(Router.Type.REDIRECT);
        return router;
    }
}
