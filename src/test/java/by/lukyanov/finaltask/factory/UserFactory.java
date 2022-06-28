package by.lukyanov.finaltask.factory;

import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.entity.UserRole;
import by.lukyanov.finaltask.entity.UserStatus;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class UserFactory {
    private static final Long id = 1L;
    private static final String email = "abv@gmail.com";
    private static final String password = "$argon2i$v=19$m=64,t=2,p=1$a1ZrZW0xQ0I3dEVsOUVjTQ$hQZ1KQzMC3cUS6+zP+GFNHjkgVmQqy7vtqj/ljrBuyE";
    private static final String plainPassword = "A12345678b";
    private static final String name = "David";
    private static final String surname = "Laid";
    private static final UserStatus status = UserStatus.ACTIVE;
    private static final UserRole role = UserRole.USER;
    private static final String phone = "29-123-45-67";
    private static final BigDecimal balance = new BigDecimal(1500);

    public static User createUser(){
        return new User.UserBuilder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .surname(surname)
                .status(status)
                .role(role)
                .phone(phone)
                .balance(balance)
                .build();
    }

    public static Map<String, String> createUserData(){
        Map<String, String> userData = new HashMap<>();
        userData.put(USER_ID, id.toString());
        userData.put(USER_EMAIL, email);
        userData.put(USER_PASS, plainPassword);
        userData.put(USER_NAME, name);
        userData.put(USER_SURNAME, surname);
        userData.put(USER_ROLE, role.name());
        userData.put(USER_STATUS, status.name());
        userData.put(USER_PHONE, phone);
        return userData;
    }
}
