package by.lukyanov.finaltask.validation;

public interface UserValidator {
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
}
