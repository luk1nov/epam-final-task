package by.lukyanov.final_task.command;

public class ParameterAndAttribute {
//    request
    public static final String COMMAND = "command";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    public static final String USER_SURNAME = "userSurname";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_PASS = "userPass";
    public static final String USER_REPEAT_PASS = "userRepeatPass";
    public static final String USER_ROLE = "userRole";
    public static final String USER_STATUS = "userStatus";
    public static final String MESSAGE = "message";
    public static final String USER = "user";
    public static final String ALL_USERS = "all_users";
    public static final String ALL_CARS = "all_cars";
    public static final String CAR_BRAND = "carBrand";
    public static final String CAR_MODEL = "carModel";
    public static final String CAR_REGULAR_PRICE = "carRegularPrice";
    public static final String CAR_SALE_PRICE = "carSalePrice";
    public static final String CAR_ACTIVE = "carActive";
    public static final String CAR_INFO_ACCELERATION = "carInfoAcceleration";
    public static final String CAR_INFO_POWER = "carInfoPower";
    public static final String CAR_INFO_DRIVETRAIN = "carInfoDrivetrain";

//    session
    public static final String LOGGED_USER = "loggedUser";

    private final String attr;

    ParameterAndAttribute(String attr) {
        this.attr = attr;
    }

    public String getAttr() {
        return attr;
    }
}
