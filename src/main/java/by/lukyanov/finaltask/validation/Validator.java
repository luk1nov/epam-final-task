package by.lukyanov.finaltask.validation;

public interface Validator {
    boolean isOneWord(String string);

    boolean isValidSurname(String surname);

    boolean isValidPassword(String password);

    boolean isValidEmail(String email);

    boolean isValidPhone(String phone);

    boolean isValidId(String id);

    boolean isValidCarModel(String model);

    boolean isValidVinCode(String vinCode);

    boolean isValidPrice(String price);

    boolean isValidAcceleration(String acceleration);

    boolean isValidPower(String power);

    boolean isLocaleExists(String locale);

    boolean isValidDateRange(String dateRange);

    boolean isValidMessage(String declineMessage);

    boolean isValidNumber(String number);
}
