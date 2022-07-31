package by.lukyanov.finaltask.validation;

/**
 * Validator interface.
 */
public interface CommonValidator {
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
     * Checks is valid id.
     * Example: "12"
     *
     * @param id id string
     * @return true if id is not null and valid, otherwise false
     */
    boolean isValidId(String id);

    /**
     * Checks is valid price.
     * Example: "400", "400.10"
     *
     * @param price price
     * @return true if price is not null and valid, otherwise false
     */
    boolean isValidPrice(String price);

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
