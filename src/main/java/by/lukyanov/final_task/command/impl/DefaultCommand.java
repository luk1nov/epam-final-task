package by.lukyanov.final_task.command.impl;

import by.lukyanov.final_task.command.Command;
import by.lukyanov.final_task.command.PagePath;
import by.lukyanov.final_task.command.Router;
import jakarta.servlet.http.HttpServletRequest;

public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setPagePath(PagePath.MAIN_PAGE);
        return router;
    }
}
