package by.lukanov.final_task.command.impl.login;

import by.lukanov.final_task.command.Command;
import by.lukanov.final_task.command.PagePath;
import by.lukanov.final_task.command.ParameterAndAttribute;
import by.lukanov.final_task.command.Router;
import by.lukanov.final_task.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.core.appender.routing.Route;

public class LogOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        session.removeAttribute(ParameterAndAttribute.LOGGED_USER);
        router.setPagePath(PagePath.MAIN_PAGE);
        return router;
    }
}
