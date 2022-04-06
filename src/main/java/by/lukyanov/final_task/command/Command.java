package by.lukyanov.final_task.command;

import by.lukyanov.final_task.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;
}
