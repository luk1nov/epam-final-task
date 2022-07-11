package by.lukyanov.finaltask.validation;

/**
 * Validator interface.
 */
public interface Validator {
    /**
     * Checks if string contains only 1 word.
     * Min length - 2, max length - 40
     * Example: "one"
     *
     * @param string validating string
     * @return true if string is not null and contains only 1 word, otherwise false
     */
    boolean isOneWord(String string);

    /**
     * Checks if surname is valid.
     * Min length - 2, max length - 45
     * Example: "hamilton", "hamilton-good"
     *
     * @param surname surname
     * @return true if surname is not null and valid, otherwise false
     */
    boolean isValidSurname(String surname);

    /**
     * Checks if password is valid.
     * Must contain at least 1 uppercase letter, 1 lowercase letter, 1 digit
     * Min length - 8, max length - 20.
     * Example: "123456Ab"
     *
     * @param password plain password
     * @return true is password is not null and valid, otherwise false
     */
    boolean isValidPassword(String password);

    /**
     * Checks is valid email.
     * Example: "email@mail.com"
     *
     * @param email email
     * @return true if email is not null and valid, otherwise false
     */
    boolean isValidEmail(String email);

    /**
     * Checks if phone number (without country code) is valid.
     * Example: "29-123-34-45"
     *
     * @param phone phone number
     * @return true if phone number is not null and valid, otherwise false
     */
    boolean isValidPhone(String phone);

    /**
     * Checks is valid id.
     * Example: "12"
     *
     * @param id id string
     * @return true if id is not null and valid, otherwise false
     */
    boolean isValidId(String id);

    /**
     * Checks is valid car model.
     * Min length - 2, max length - 20
     * Example: "RS4"
     *
     * @param model car model
     * @return true if car model is not null and valid, otherwise false
     */
    boolean isValidCarModel(String model);

    /**
     * Checks if vin code is valid.
     * Accepting all digits and cyrillic symbols except "I", "O", "Q" and must be 17 characters long.
     * Example: "A8UFBAW6PN123AERK"
     *
     * @param vinCode vin code
     * @return true if vin code is not null and valid, otherwise false
     */
    boolean isValidVinCode(String vinCode);

    /**
     * Checks is valid price.
     * Example: "400", "400.10"
     *
     * @param price price
     * @return true if price is not null and valid, otherwise false
     */
    boolean isValidPrice(String price);

    /**
     * Checks is valid car acceleration.
     * Example: "4", "4.1"
     *
     * @param acceleration car acceleration
     * @return true if acceleration id not null and valid, otherwise false
     */
    boolean isValidAcceleration(String acceleration);

    /**
     * Checks is valid car power.
     * min - 100, max - 999
     * Example: "400"
     *
     * @param power car power
     * @return true if car power is not null and valid, otherwise false
     */
    boolean isValidPower(String power);

    /**
     * Checks is locale exists.
     * Example: "ru", "en"
     *
     * @param locale locale string
     * @return true if locale equals "ru" or "en", otherwise false
     */
    boolean isLocaleExists(String locale);

    /**
     * Checks is valid date range.
     * Example: "2022-01-01", "2022-01-01:2022-02-01"
     *
     * @param dateRange date range
     * @return true if date range is not null and valid, otherwise false
     */
    boolean isValidDateRange(String dateRange);

    /**
     * Checks is valid message.
     * Min length - 1, max length - 200
     * Example: "Hello, welcome to los pollos hermanos"
     *
     * @param message decline user approve message or order report message
     * @return true if message is not null and valid, otherwise false
     */
    boolean isValidMessage(String message);

    /**
     * Checks is string contains only digits.
     * Example: "53"
     *
     * @param number number string
     * @return true if number is not null and valid, otherwise false
     */
    boolean isValidNumber(String number);

    /**
     * Checks if search query is valid.
     * Min length - 1, max length - 40
     * Example: "audi"
     *
     * @param searchQuery search query string
     * @return true if search query is not null and valid, otherwise false
     */
    boolean isValidSearchQuery(String searchQuery);
}
