package by.lukanov.final_task.command;

import by.lukanov.final_task.command.impl.DefaultCommand;
import by.lukanov.final_task.command.impl.admin.EditUserCommand;
import by.lukanov.final_task.command.impl.admin.ToEditUserCommand;
import by.lukanov.final_task.command.impl.login.LogOutCommand;
import by.lukanov.final_task.command.impl.login.SignInCommand;
import by.lukanov.final_task.command.impl.login.SignUpCommand;
import by.lukanov.final_task.command.impl.navigation.ToSignInCommand;
import by.lukanov.final_task.command.impl.navigation.ToSignUpCommand;
import by.lukanov.final_task.command.impl.admin.FindAllUsersCommand;

public enum CommandType {
    DEFAULT(new DefaultCommand()),
    SIGNUP(new SignUpCommand()),
    SIGNIN(new SignInCommand()),
    LOG_OUT(new LogOutCommand()),
    TO_SIGNIN_PAGE(new ToSignInCommand()),
    TO_SIGNUP_PAGE(new ToSignUpCommand()),
    TO_ADMIN_ALL_USERS_PAGE(new FindAllUsersCommand()),
    TO_ADMIN_EDIT_USER_PAGE(new ToEditUserCommand()),
    ADMIN_EDIT_USER(new EditUserCommand());

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
