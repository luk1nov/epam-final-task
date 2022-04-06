package by.lukyanov.final_task.command;

import by.lukyanov.final_task.command.impl.DefaultCommand;
import by.lukyanov.final_task.command.impl.admin.*;
import by.lukyanov.final_task.command.impl.login.LogOutCommand;
import by.lukyanov.final_task.command.impl.login.SignInCommand;
import by.lukyanov.final_task.command.impl.login.SignUpCommand;
import by.lukyanov.final_task.command.impl.navigation.ToSignInCommand;
import by.lukyanov.final_task.command.impl.navigation.ToSignUpCommand;

public enum CommandType {
    DEFAULT(new DefaultCommand()),
    SIGNUP(new SignUpCommand()),
    SIGNIN(new SignInCommand()),
    LOG_OUT(new LogOutCommand()),
    TO_SIGNIN_PAGE(new ToSignInCommand()),
    TO_SIGNUP_PAGE(new ToSignUpCommand()),
    TO_ADMIN_ALL_USERS_PAGE(new FindAllUsersCommand()),
    TO_ADMIN_EDIT_USER_PAGE(new ToEditUserCommand()),
    ADMIN_EDIT_USER(new EditUserCommand()),
    ADMIN_DELETE_USER(new DeleteUserCommand()),
    TO_ADMIN_ALL_CARS_PAGE(new FindAllCarsCommand()),
    ADD_NEW_CAR(new AddNewCarCommand()),
    TO_ADMIN_ADD_NEW_CAR_PAGE(new ToAddNewCarCommand());

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
