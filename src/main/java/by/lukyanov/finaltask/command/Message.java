package by.lukyanov.finaltask.command;

public final class Message {
    public static final String PASSWORD_MISMATCH = "Password doesn't match";
    public static final String USER_NOT_ADDED = "User not added";
    public static final String USER_EXISTS = "User already exists";
    public static final String POOL_FILL_ERROR = "Connection pool not filled";
    public static final String POOL_NOT_FULL = "Connection pool not full filled. Trying again...";
    public static final String DRIVER_NOT_REGISTER = "Driver register error";
    public static final String RELEASE_CON_EXCEPT = "Interrupted exception in release connection method";
    public static final String GET_CON_EXCEPT = "Interrupted exception in get connection method";
    public static final String DESTROY_POOL_SQL = "SQL exception in get destroy pool method";
    public static final String DESTROY_POOL_INTERRUPTED = "Interrupted exception in destroy pool method";
    public static final String DEREGISTER_DRIVER_SQL = "SQL exception in deregister driver method";
    public static final String INCORRECT_EMAIL_OR_LOGIN = "Incorrect password or login";
    public static final String CAN_NOT_EDIT_USER = "Can not edit this user";
    public static final String USER_NOT_UPDATED = "User update failed";
    public static final String USER_NOT_DELETED = "User not deleted";
    public static final String READ_PROPERTIES_ERROR = "Error reading properties for db";
    public static final String CAR_NOT_EDITED = "Car not edited";
    public static final String CAR_NOT_ADDED = "Car not added";
    public static final String INFO_NOT_UPDATED = "error.info_not_updated";
    public static final String CAR_NOT_FOUND = "error.car_not_found";
    public static final String USER_NOT_VERIFIED = "label.user_not_verified";
    public static final String USER_VERIFIED = "success.user_verified";
    public static final String USER_NOT_DECLINED = "label.user_not_declined";
    public static final String REPORT_NOT_FOUND = "label.report_not_found";
    private Message() {
    }
}
