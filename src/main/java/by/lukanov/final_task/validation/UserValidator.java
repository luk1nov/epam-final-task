package by.lukanov.final_task.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private static final String NAME_PATTERN = "^[A-Za-zА-Яа-яЁё]{2,}$";
    private static final String SURNAME_PATTERN = "^[A-Za-zА-Яа-яЁё]{2,}-[A-Za-zА-Яа-яЁё]{2,}|[A-Za-zА-Яа-яЁё]{2,}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[a-z0-9_-]+(\\.[a-z0-9_-]+)*@[^-][a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,})$";
    private static final String ID_PATTERN = "^\\d{1,6}$";

    public static boolean isValidName(String name){
        Pattern regex = Pattern.compile(NAME_PATTERN);
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

}
