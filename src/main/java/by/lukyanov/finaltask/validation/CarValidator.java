package by.lukyanov.finaltask.validation;

public interface CarValidator {
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


}
