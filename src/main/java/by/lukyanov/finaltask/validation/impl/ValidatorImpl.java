package by.lukyanov.finaltask.validation.impl;

import by.lukyanov.finaltask.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorImpl implements Validator {
    private static final String ONE_WORD_PATTERN = "^[A-Za-zА-Яа-яЁё]{2,40}$";
    private static final String SURNAME_PATTERN = "^[A-Za-zА-Яа-яЁё]{2,20}-[A-Za-zА-Яа-яЁё]{2,20}$|^[A-Za-zА-Яа-яЁё]{2,45}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[a-z0-9_-]+(\\.[a-z0-9_-]+)*@[^-][a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,})$";
    private static final String PHONE_PATTERN = "^\\d{2}-\\d{3}-\\d{2}-\\d{2}$";
    private static final String ID_PATTERN = "^\\d{1,6}$";
    private static final String CAR_MODEL_PATTERN = "^[A-Za-zА-Яа-яЁё\\-0-9]{2,20}$";
    private static final String PRICE_PATTERN = "^\\d{1,5}\\.\\d{1,2}$|^\\d{1,5}$";
    private static final String CAR_POWER_PATTERN = "^\\d{3}$";
    private static final String CAR_ACCELERATION_PATTERN = "^([1-5][0-9]|[1-9])(\\.[0-9])?$";
    private static final String LOCALE_PATTERN = "^en_US$|^ru_RU$";
    private static final String BOOLEAN_PATTERN = "^true$|^false$";
    private static ValidatorImpl instance;

    private ValidatorImpl() {
    }

    public static ValidatorImpl getInstance(){
        if(instance == null){
            instance = new ValidatorImpl();
        }
        return instance;
    }

    public boolean isOneWord(String name){
        return name != null && name.matches(ONE_WORD_PATTERN);
    }

    public boolean isValidSurname(String surname){
        return surname != null && surname.matches(SURNAME_PATTERN);
    }

    public boolean isValidPassword(String password){
        return password != null && password.matches(PASSWORD_PATTERN);
    }

    public boolean isValidEmail(String email){
        return email != null && email.matches(EMAIL_PATTERN);
    }

    public boolean isValidPhone(String phone){
        return phone != null && phone.matches(PHONE_PATTERN);
    }

    public boolean isValidId(String id){
        return id != null && id.matches(ID_PATTERN);
    }

    public boolean isStringBoolean(String string){
        return string != null && string.matches(BOOLEAN_PATTERN);
    }

    public boolean isValidCarModel(String model){
        return model != null && model.matches(CAR_MODEL_PATTERN);
    }

    public boolean isValidPrice(String price){
        return price != null && price.matches(PRICE_PATTERN);
    }

    public boolean isValidAcceleration(String acceleration){
        return acceleration != null && acceleration.matches(CAR_ACCELERATION_PATTERN);
    }

    public boolean isValidPower(String power){
        return power != null && power.matches(CAR_POWER_PATTERN);
    }

    public boolean isLocaleExists(String locale){
        return locale != null && locale.matches(LOCALE_PATTERN);
    }

}
