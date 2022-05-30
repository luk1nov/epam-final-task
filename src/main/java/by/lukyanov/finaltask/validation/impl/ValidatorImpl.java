package by.lukyanov.finaltask.validation.impl;

import by.lukyanov.finaltask.validation.Validator;

public class ValidatorImpl implements Validator {
    private static final String ONE_WORD_PATTERN = "^[\\p{Alpha}А-яЁё]{2,40}$";
    private static final String SURNAME_PATTERN = "^[\\p{Alpha}А-яЁё]{2,20}-[\\p{Alpha}А-яЁё]{2,20}$|^[\\p{Alpha}А-яЁё]{2,45}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[\\w\\-]+(\\.[\\w\\-]+)*@[^-][\\w\\-]+(\\.[\\p{Alpha}\\d\\-]+)*(\\.[\\p{Alpha}]{2,})$";
    private static final String PHONE_PATTERN = "^\\d{2}-\\d{3}-\\d{2}-\\d{2}$";
    private static final String ID_PATTERN = "^\\d{1,6}$";
    private static final String CAR_MODEL_PATTERN = "^([\\p{Alpha}А-яЁё\\-\\d]\\s?){2,20}$";
    private static final String VIN_CODE_PATTERN = "^[A-HJ-NPR-Z0-9]{17}$";
    private static final String PRICE_PATTERN = "^\\d{1,5}\\.\\d{1,2}$|^\\d{1,5}$";
    private static final String CAR_POWER_PATTERN = "^\\d{3}$";
    private static final String CAR_ACCELERATION_PATTERN = "^([1-5]\\d|[1-9])(\\.\\d)?$";
    private static final String LOCALE_PATTERN = "^en_US$|^ru_RU$";
    private static final String DATE_RANGE_PATTERN = "^\\d{4}-\\d{2}-\\d{2}\\sto\\s\\d{4}-\\d{2}-\\d{2}$|^\\d{4}-\\d{2}-\\d{2}$";
    private static final String MESSAGE_PATTERN = "^[\\s\\wА-яЁё.,!?]{0,200}$";
    private static final String NUMBER_PATTERN = "^\\d+$";
    private static ValidatorImpl instance;

    private ValidatorImpl() {
    }

    public static ValidatorImpl getInstance(){
        if(instance == null){
            instance = new ValidatorImpl();
        }
        return instance;
    }

    @Override
    public boolean isOneWord(String string){
        return string != null && string.matches(ONE_WORD_PATTERN);
    }

    @Override
    public boolean isValidSurname(String surname){
        return surname != null && surname.matches(SURNAME_PATTERN);
    }

    @Override
    public boolean isValidPassword(String password){
        return password != null && password.matches(PASSWORD_PATTERN);
    }

    @Override
    public boolean isValidEmail(String email){
        return email != null && email.matches(EMAIL_PATTERN);
    }

    @Override
    public boolean isValidPhone(String phone){
        return phone != null && phone.matches(PHONE_PATTERN);
    }

    @Override
    public boolean isValidId(String id){
        return id != null && id.matches(ID_PATTERN);
    }

    @Override
    public boolean isValidCarModel(String model){
        return model != null && model.matches(CAR_MODEL_PATTERN);
    }

    @Override
    public boolean isValidVinCode(String vinCode) {
        return vinCode != null && vinCode.matches(VIN_CODE_PATTERN);
    }

    @Override
    public boolean isValidPrice(String price){
        return price != null && price.matches(PRICE_PATTERN);
    }

    @Override
    public boolean isValidAcceleration(String acceleration){
        return acceleration != null && acceleration.matches(CAR_ACCELERATION_PATTERN);
    }

    @Override
    public boolean isValidPower(String power){
        return power != null && power.matches(CAR_POWER_PATTERN);
    }

    @Override
    public boolean isLocaleExists(String locale){
        return locale != null && locale.matches(LOCALE_PATTERN);
    }

    @Override
    public boolean isValidDateRange(String dateRange) {
        return dateRange != null && dateRange.matches(DATE_RANGE_PATTERN);
    }

    @Override
    public boolean isValidMessage(String declineMessage) {
        return declineMessage != null && declineMessage.matches(MESSAGE_PATTERN);
    }

    @Override
    public boolean isValidNumber(String number) {
        return number != null && number.matches(NUMBER_PATTERN);
    }
}
