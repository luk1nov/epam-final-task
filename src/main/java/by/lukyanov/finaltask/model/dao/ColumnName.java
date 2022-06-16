package by.lukyanov.finaltask.model.dao;

public final class ColumnName {
    /* user */
    public static final String USER_ID = "user_id";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASS = "password";
    public static final String USER_NAME = "name";
    public static final String USER_SURNAME = "surname";
    public static final String USER_STATUS = "user_status";
    public static final String USER_ROLE = "user_role";
    public static final String USER_DRIVER_LICENSE = "driver_license_photo";
    public static final String USER_PHONE = "phone";
    public static final String USER_BALANCE = "balance";

    /* car */
    public static final String CAR_ID = "car_id";
    public static final String CAR_BRAND = "brand";
    public static final String CAR_MODEL = "model";
    public static final String CAR_VIN_CODE = "vin_code";
    public static final String CAR_REGULAR_PRICE = "regular_price";
    public static final String CAR_SALE_PRICE = "sale_price";
    public static final String CAR_IS_ACTIVE = "is_active";
    public static final String CAR_IMG = "image";

    /* car info*/
    public static final String CAR_INFO_ACCELERATION = "acceleration";
    public static final String CAR_INFO_POWER = "power";
    public static final String CAR_INFO_DRIVETRAIN = "drivetrain";

    /* car category */
    public static final String CAR_CAT_ID = "car_category_id";
    public static final String CAR_CAT_TITLE = "car_category_title";

    /* order */
    public static final String ORDER_ID = "order_id";
    public static final String ORDER_BEGIN_DATE = "begin_date";
    public static final String ORDER_END_DATE = "end_date";
    public static final String ORDER_STATUS = "order_status";
    public static final String ORDER_MESSAGE = "message";
    public static final String ORDER_PRICE = "price";

    /* order report */
    public static final String REPORT_ID = "report_id";
    public static final String REPORT_PHOTO = "report_photo";
    public static final String REPORT_TEXT = "report_text";
    public static final String REPORT_STATUS = "report_status";

    private ColumnName() {
    }
}
