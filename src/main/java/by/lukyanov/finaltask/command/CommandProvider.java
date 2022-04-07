package by.lukyanov.finaltask.command;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandProvider {
    private static final Logger logger = LogManager.getLogger();

    public CommandProvider() {
    }

    public static Command defineCommand(HttpServletRequest request){
        String command = request.getParameter(ParameterAndAttribute.COMMAND.toLowerCase());

        if(command == null){
            logger.error("command null");
            return CommandType.DEFAULT.getCommand();
        }

        try {
            return CommandType.valueOf(command.toUpperCase()).getCommand();
        } catch (IllegalArgumentException e){
            logger.error(e.getMessage());
            return CommandType.DEFAULT.getCommand();
        }
    }
}
