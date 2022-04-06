package by.lukyanov.final_task.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private static final String ONE_WORD_PATTERN = "^[A-Za-zА-Яа-яЁё]{2,40}$";
    private static final String SURNAME_PATTERN = "^[A-Za-zА-Яа-яЁё]{2,20}-[A-Za-zА-Яа-яЁё]{2,20}$|^[A-Za-zА-Яа-яЁё]{2,45}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[a-z0-9_-]+(\\.[a-z0-9_-]+)*@[^-][a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,})$";
    private static final String ID_PATTERN = "^\\d{1,6}$";
    private static final String CAR_MODEL_PATTERN = "^[A-Za-zА-Яа-яЁё\\-0-9]{2,20}$";
    private static final String PRICE_PATTERN = "^\\d{1,5}\\.\\d{1,2}$|^\\d{1,5}$";
    private static final String CAR_POWER_PATTERN = "^\\d{3}$";
    private static final String CAR_ACCELERATION_PATTERN = "^[1-5][0-9]?\\.[0-9]{1,2}$|^[1-5][0-9]?$";

    public static boolean isOneWord(String name){
        Pattern regex = Pattern.compile(ONE_WORD_PATTERN);
        Matcher matcher = regex.matcher(name);
        return matcher.matches();
    }

    public static boolean isValidSurname(String surname){
        Pattern regex = Pattern.compile(SURNAME_PATTERN);
        Matcher matcher = regex.matcher(surname);
        return matcher.matches();
    }

    public static boolean isValidPassword(String password){
        Pattern regex = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = regex.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidEmail(String email){
        Pattern regex = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidId(String id){
        Pattern regex = Pattern.compile(ID_PATTERN);
        Matcher matcher = regex.matcher(id);
        return matcher.matches();
    }

    public static boolean isValidCarActive(String active){
        // todo null check
        if (active.equals("true") || active.equals("false")){
            return true;
        }
        return false;
    }

    public static boolean isValidCarModel(String model){
        Pattern regex = Pattern.compile(CAR_MODEL_PATTERN);
        Matcher matcher = regex.matcher(model);
        return matcher.matches();
    }

    public static boolean isValidPrice(String price){
        Pattern regex = Pattern.compile(PRICE_PATTERN);
        Matcher matcher = regex.matcher(price);
        return matcher.matches();
    }

    public static boolean isValidAcceleration(String acceleration){
        Pattern regex = Pattern.compile(CAR_ACCELERATION_PATTERN);
        Matcher matcher = regex.matcher(acceleration);
        return matcher.matches();
    }

    public static boolean isValidPower(String power){
        Pattern regex = Pattern.compile(CAR_POWER_PATTERN);
        Matcher matcher = regex.matcher(power);
        return matcher.matches();
    }

}
