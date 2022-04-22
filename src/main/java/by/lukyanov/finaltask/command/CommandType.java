package by.lukyanov.finaltask.command;

import by.lukyanov.finaltask.command.impl.ChangeLocaleCommand;
import by.lukyanov.finaltask.command.impl.DefaultCommand;
import by.lukyanov.finaltask.command.impl.admin.car.*;
import by.lukyanov.finaltask.command.impl.admin.carcategory.*;
import by.lukyanov.finaltask.command.impl.admin.user.*;
import by.lukyanov.finaltask.command.impl.login.LogOutCommand;
import by.lukyanov.finaltask.command.impl.login.SignInCommand;
import by.lukyanov.finaltask.command.impl.login.SignUpCommand;
import by.lukyanov.finaltask.command.impl.navigation.ToCarCategoryCommand;
import by.lukyanov.finaltask.command.impl.navigation.ToSignInCommand;
import by.lukyanov.finaltask.command.impl.navigation.ToSignUpCommand;
import by.lukyanov.finaltask.command.impl.user.RefillBalanceCommand;
import by.lukyanov.finaltask.command.impl.user.ToRefillBalanceCommand;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum CommandType {
    //USERS
    DEFAULT(new DefaultCommand()),
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    SIGNUP(new SignUpCommand()),
    SIGNIN(new SignInCommand()),
    LOG_OUT(new LogOutCommand()),
    TO_SIGNIN_PAGE(new ToSignInCommand()),
    TO_SIGNUP_PAGE(new ToSignUpCommand()),
    TO_CAR_CATEGORY_PAGE(new ToCarCategoryCommand()),
    TO_REFILL_BALANCE(new ToRefillBalanceCommand()),
    REFILL_BALANCE(new RefillBalanceCommand()),

    //ADMIN
    ADMIN_TO_ALL_USERS(new FindAllUsersCommand()),
    ADMIN_TO_ADD_NEW_USER(new ToAddNewUserCommand()),
    ADMIN_TO_EDIT_USER(new ToEditUserCommand()),
    ADMIN_EDIT_USER(new EditUserCommand()),
    ADMIN_DELETE_USER(new DeleteUserCommand()),
    ADMIN_TO_ALL_CARS(new FindAllCarsCommand()),
    ADMIN_TO_ADD_NEW_CAR(new ToAddNewCarCommand()),
    ADMIN_ADD_NEW_CAR(new AddNewCarCommand()),
    ADMIN_TO_EDIT_CAR(new ToEditCarCommand()),
    ADMIN_EDIT_CAR(new EditCarCommand()),
    ADMIN_DELETE_CAR(new DeleteCarCommand()),
    ADMIN_CHANGE_CAR_ACTIVE_STATUS(new ChangeCarActiveCommand()),
    ADMIN_TO_ADD_NEW_CAR_CATEGORY(new ToAddNewCarCategoryCommand()),
    ADMIN_ADD_NEW_CAR_CATEGORY(new AddNewCarCategoryCommand()),
    ADMIN_TO_ALL_CAR_CATEGORIES(new FindAllCarCategoriesCommand()),
    ADMIN_TO_EDIT_CAR_CATEGORY(new ToEditCarCategoryCommand()),
    ADMIN_DELETE_CAR_CATEGORY(new DeleteCarCategoryCommand()),
    ADMIN_EDIT_CAR_CATEGORY(new EditCarCategoryCommand());

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
        Command definedCommand;
        if(command == null){
            logger.error("command null");
            definedCommand = DEFAULT.getCommand();
        } else {
            try {
                definedCommand = valueOf(command.toUpperCase()).getCommand();
            } catch (IllegalArgumentException e){
                logger.error(e.getMessage());
                definedCommand = DEFAULT.getCommand();
            }
        }
        return definedCommand;
    }
}
