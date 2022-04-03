package by.lukanov.final_task.command;

import by.lukanov.final_task.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;
}
