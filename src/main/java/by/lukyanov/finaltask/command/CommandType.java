package by.lukyanov.finaltask.command;

import by.lukyanov.finaltask.command.impl.DefaultCommand;
import by.lukyanov.finaltask.command.impl.admin.*;
import by.lukyanov.finaltask.command.impl.login.LogOutCommand;
import by.lukyanov.finaltask.command.impl.login.SignInCommand;
import by.lukyanov.finaltask.command.impl.login.SignUpCommand;
import by.lukyanov.finaltask.command.impl.navigation.ToSignInCommand;
import by.lukyanov.finaltask.command.impl.navigation.ToSignUpCommand;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum CommandType {
    //USERS
    DEFAULT(new DefaultCommand()),
    SIGNUP(new SignUpCommand()),
    SIGNIN(new SignInCommand()),
    LOG_OUT(new LogOutCommand()),
    TO_SIGNIN_PAGE(new ToSignInCommand()),
    TO_SIGNUP_PAGE(new ToSignUpCommand()),
    //ADMIN
    TO_ADMIN_ALL_USERS_PAGE(new FindAllUsersCommand()),
    TO_ADMIN_EDIT_USER_PAGE(new ToEditUserCommand()),
    ADMIN_EDIT_USER(new EditUserCommand()),
    ADMIN_DELETE_USER(new DeleteUserCommand()),
    TO_ADMIN_ALL_CARS_PAGE(new FindAllCarsCommand()),
    ADD_NEW_CAR(new AddNewCarCommand()),
    TO_ADMIN_ADD_NEW_CAR_PAGE(new ToAddNewCarCommand()),
    TO_EDIT_CAR_PAGE(new ToEditCarCommand()),
    EDIT_CAR(new DefaultCommand()); //TODO

    private static final Logger logger = LogManager.getLogger();
    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public static Command define(HttpServletRequest request){
        String command = request.getParameter(ParameterAndAttribute.COMMAND.toLowerCase());

        if(command == null){
            logger.error("command null");
            return DEFAULT.getCommand();
        }

        try {
            return valueOf(command.toUpperCase()).getCommand();
        } catch (IllegalArgumentException e){
            logger.error(e.getMessage());
            return DEFAULT.getCommand();
        }
    }
}
