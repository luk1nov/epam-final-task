package by.lukyanov.finaltask.validation;

/**
 * Validator interface.
 */
public interface Validator {
    /**
     * Checks if string contains only 1 word.
     *
     * @param string validating string
     * @return true if string is not null and contains only 1 word, otherwise false
     */
    boolean isOneWord(String string);

    /**
     * Checks if surname is valid.
     *
     * @param surname surname
     * @return true if surname is not null and valid, otherwise false
     */
    boolean isValidSurname(String surname);

    /**
     * Checks if password contains at least 1 uppercase letter, 1 lowercase letter,
     * 1 digit and has length from 8 to 20 symbols.
     *
     * @param password plain password
     * @return true is password is not null and valid, otherwise false
     */
    boolean isValidPassword(String password);

    /**
     * Checks is valid email
     *
     * @param email email
     * @return true if email is not null and valid, otherwise false
     */
    boolean isValidEmail(String email);

    /**
     * Checks if phone number (without country code) is valid.
     *
     * @param phone phone number
     * @return true if phone number is not null and valid, otherwise false
     */
    boolean isValidPhone(String phone);

    /**
     * Checks is valid id.
     *
     * @param id id string
     * @return true if id is not null and valid, otherwise false
     */
    boolean isValidId(String id);

    /**
     * Checks is valid car model.
     *
     * @param model car model
     * @return true if car model is not null and valid, otherwise false
     */
    boolean isValidCarModel(String model);

    /**
     * Checks if vin code is valid.
     *
     * @param vinCode vin code
     * @return true if vin code is not null and valid, otherwise false
     */
    boolean isValidVinCode(String vinCode);

    /**
     * Checks is valid price.
     *
     * @param price price
     * @return true if price is not null and valid, otherwise false
     */
    boolean isValidPrice(String price);

    /**
     * Checks is valid car acceleration.
     *
     * @param acceleration car acceleration
     * @return true if acceleration id not null and valid, otherwise false
     */
    boolean isValidAcceleration(String acceleration);

    /**
     * Checks is valid car power.
     *
     * @param power car power
     * @return true if car power is not null and valid, otherwise false
     */
    boolean isValidPower(String power);

    /**
     * Checks is locale exists
     *
     * @param locale locale string
     * @return true if locale equals "ru" or "en", otherwise false
     */
    boolean isLocaleExists(String locale);

    /**
     * Checks is valid date range
     *
     * @param dateRange date range
     * @return true if date range is not null and valid, otherwise false
     */
    boolean isValidDateRange(String dateRange);

    /**
     * Checks is valid message
     *
     * @param message decline user approve message or order report message
     * @return true if message is not null and valid, otherwise false
     */
    boolean isValidMessage(String message);

    /**
     * Checks is string contains only digits
     *
     * @param number number string
     * @return true if number is not null and valid, otherwise false
     */
    boolean isValidNumber(String number);

    /**
     * Checks if search query has at least 1 symbol, but not more than 40.
     *
     * @param searchQuery search query string
     * @return true if search query is not null and valid, otherwise false
     */
    boolean isValidSearchPattern(String searchQuery);
}
