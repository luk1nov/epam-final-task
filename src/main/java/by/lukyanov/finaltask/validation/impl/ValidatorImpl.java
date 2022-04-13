package by.lukyanov.finaltask.validation.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorImpl {
    private static final String ONE_WORD_PATTERN = "^[A-Za-zА-Яа-яЁё]{2,40}$";
    private static final String SURNAME_PATTERN = "^[A-Za-zА-Яа-яЁё]{2,20}-[A-Za-zА-Яа-яЁё]{2,20}$|^[A-Za-zА-Яа-яЁё]{2,45}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[a-z0-9_-]+(\\.[a-z0-9_-]+)*@[^-][a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,})$";
    private static final String ID_PATTERN = "^\\d{1,6}$";
    private static final String CAR_MODEL_PATTERN = "^[A-Za-zА-Яа-яЁё\\-0-9]{2,20}$";
    private static final String PRICE_PATTERN = "^\\d{1,5}\\.\\d{1,2}$|^\\d{1,5}$";
    private static final String CAR_POWER_PATTERN = "^\\d{3}$";
    private static final String CAR_ACCELERATION_PATTERN = "^([1-5][0-9]|[1-9])(\\.[0-9])?$";
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
        return name != null ? name.matches(ONE_WORD_PATTERN) : false;
    }

    public boolean isValidSurname(String surname){
        Pattern regex = Pattern.compile(SURNAME_PATTERN);
        Matcher matcher = regex.matcher(surname);
        return matcher.matches();
    }

    public boolean isValidPassword(String password){
        Pattern regex = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = regex.matcher(password);
        return matcher.matches();
    }

    public boolean isValidEmail(String email){
        Pattern regex = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }

    public boolean isValidId(String id){
        Pattern regex = Pattern.compile(ID_PATTERN);
        Matcher matcher = regex.matcher(id);
        return matcher.matches();
    }

    public boolean isValidCarActive(String active){
        // todo null check
        if (active.equals("true") || active.equals("false")){
            return true;
        }
        return false;
    }

    public boolean isValidCarModel(String model){
        Pattern regex = Pattern.compile(CAR_MODEL_PATTERN);
        Matcher matcher = regex.matcher(model);
        return matcher.matches();
    }

    public boolean isValidPrice(String price){
        Pattern regex = Pattern.compile(PRICE_PATTERN);
        Matcher matcher = regex.matcher(price);
        return matcher.matches();
    }

    public boolean isValidAcceleration(String acceleration){
        Pattern regex = Pattern.compile(CAR_ACCELERATION_PATTERN);
        Matcher matcher = regex.matcher(acceleration);
        return matcher.matches();
    }

    public boolean isValidPower(String power){
        Pattern regex = Pattern.compile(CAR_POWER_PATTERN);
        Matcher matcher = regex.matcher(power);
        return matcher.matches();
    }

}
