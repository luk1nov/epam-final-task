package by.lukyanov.finaltask.command;

public final class Message {
    public static final String POOL_FILL_ERROR = "Connection pool not filled";
    public static final String POOL_NOT_FULL = "Connection pool not full filled. Trying again...";
    public static final String DRIVER_NOT_REGISTER = "Driver register error";
    public static final String RELEASE_CON_EXCEPT = "Interrupted exception in release connection method";
    public static final String GET_CON_EXCEPT = "Interrupted exception in get connection method";
    public static final String DESTROY_POOL_SQL = "SQL exception in get destroy pool method";
    public static final String DESTROY_POOL_INTERRUPTED = "Interrupted exception in destroy pool method";
    public static final String DEREGISTER_DRIVER_SQL = "SQL exception in deregister driver method";
    public static final String CAN_NOT_EDIT_USER = "Can not edit this user";
    public static final String READ_PROPERTIES_ERROR = "Error reading properties for db";
    public static final String CAR_NOT_FOUND = "error.car_not_found";
    public static final String USER_NOT_VERIFIED = "label.user_not_verified";
    public static final String USER_VERIFIED = "success.user_verified";
    public static final String USER_NOT_DECLINED = "fail.user_not_declined";
    public static final String USER_DECLINED = "success.user_declined";
    public static final String REPORT_NOT_FOUND = "fail.report_not_found";
    public static final String INVALID_DATA = "fail.invalid_data";
    public static final String NOT_ENOUGH_MONEY = "fail.not_enough_money";
    public static final String USER_NOT_ACTIVE = "fail.user_not_active";
    public static final String CAR_ADDED = "success.car_added";
    public static final String CAR_NOT_ADDED = "fail.car_not_added";
    public static final String CAR_EXISTS = "fail.car_exists_with_such_vin";
    public static final String CAR_STATUS_CHANGED = "success.car_status_changed";
    public static final String CAR_STATUS_NOT_CHANGED = "fail.car_status_not_changed";
    public static final String ACTIVE_ORDERS_WITH_THIS_CAR = "fail.active_orders_with_this_car";
    public static final String CAR_DELETED = "success.car_deleted";
    public static final String CAR_NOT_DELETED = "fail.car_not_deleted";
    public static final String CATEGORY_ADDED = "success.category_added";
    public static final String CATEGORY_NOT_ADDED = "fail.category_not_added";
    public static final String CATEGORY_EXISTS = "fail.category_exists";
    public static final String CAR_NOT_EDITED = "fail.car_not_edited";
    public static final String CAR_EDITED = "success.car_edited";
    public static final String CATEGORY_NOT_EDITED = "fail.category_not_edited";
    public static final String CATEGORY_EDITED = "success.category_edited";
    public static final String CATEGORY_DELETED = "success.category_deleted";
    public static final String CATEGORY_NOT_DELETED = "fail.category_not_deleted";
    public static final String CATEGORY_NOT_EXISTS = "fail.category_not_exists";
    public static final String USER_NOT_EXISTS = "fail.user_not_exists";
    public static final String USER_NOT_DELETED = "fail.user_not_deleted";
    public static final String USER_DELETED = "success.user_deleted";
    public static final String CAR_NOT_EXISTS = "fail.car_not_exists";
    public static final String INCORRECT_EMAIL_OR_PASS = "fail.incorrect_email_or_pass";
    public static final String USER_NOT_ADDED = "fail.user_not_added";
    public static final String USER_ADDED = "success.user_added";
    public static final String USER_EXISTS = "fail.user_exists";
    public static final String ORDER_DELETED = "success.order_deleted";
    public static final String ORDER_NOT_DELETED = "fail.order_not_deleted";
    public static final String ORDER_DECLINED = "success.order_declined";
    public static final String ORDER_NOT_DECLINED = "fail.order_not_declined";
    public static final String ORDER_ACCEPTED = "success.order_accepted";
    public static final String ORDER_NOT_ACCEPTED = "fail.order_not_accepted";
    public static final String USER_NOT_EDITED = "fail.user_not_edited";
    public static final String USER_EDITED = "success.user_edited";
    public static final String INFO_NOT_UPDATED = "fail.info_not_updated";
    public static final String INFO_UPDATED = "success.info_updated";
    public static final String DRIVER_LICENSE_UPDATED = "success.driver_license_updated";
    public static final String DRIVER_LICENSE_NOT_UPDATED = "fail.driver_license_not_updated";
    public static final String PASSWORD_CHANGED = "success.password_changed";
    public static final String PASSWORD_NOT_CHANGED = "fail.password_not_changed";
    public static final String ORDER_CANCELED = "success.order_canceled";
    public static final String ORDER_NOT_CANCELED = "fail.order_not_canceled";
    public static final String ORDER_NOT_FINISHED = "fail.order_not_finished";
    public static final String ORDER_FINISHED = "success.order_finished";
    public static final String ORDER_NOT_FOUND = "fail.order_not_found";
    public static final String BALANCE_NOT_REFILLED = "fail.balance_not_refilled";
    public static final String BALANCE_REFILLED = "success.balance_refilled";
    public static final String INCORRECT_CURRENT_PASS = "fail.incorrect_current_pass";
    public static final String PASSWORD_MISMATCH = "fail.password_mismatch";








    private Message() {
    }
}
