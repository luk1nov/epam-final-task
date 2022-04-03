package by.lukanov.final_task.command;

public enum ParameterAndAttribute {
    COMMAND("command"),
    REGISTRATION_PASSWORD("register_password"),
    REGISTRATION_REPEATED_PASSWORD("register_repeated_password"),
    REGISTRATION_EMAIL("register_email"),
    REGISTRATION_NAME("register_name"),
    REGISTRATION_SURNAME("register_surname"),
    USER_NAME("userName"),
    USER_SURNAME("userSurname"),
    USER_EMAIL("userEmail"),
    USER_PASS("userPass"),
    USER_ROLE("userRole"),
    USER_STATUS("userStatus"),
    MESSAGE("message"),
    USER("user"),
    LOGIN_EMAIL("login_email"),
    LOGIN_PASS("login_password"),
    ALL_USERS("all_users"),
    USER_ID("user_id"),
    ADMIN_EDIT_USER("admin_edit_user");

    private final String attr;

    ParameterAndAttribute(String attr) {
        this.attr = attr;
    }

    public String getAttr() {
        return attr;
    }
}
