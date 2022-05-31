package by.lukyanov.finaltask.mapper.impl;

import by.lukyanov.finaltask.entity.*;
import by.lukyanov.finaltask.mapper.RowMapper;
import by.lukyanov.finaltask.util.ImageEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRowMapper implements RowMapper<User> {
    private static final String PHONE_CODE_BY = "\\+375-";
    private static final Logger logger = LogManager.getLogger();
    private static UserRowMapper instance;

    private UserRowMapper() {
    }

    public static UserRowMapper getInstance(){
        if(instance == null){
            instance = new UserRowMapper();
        }
        return instance;
    }

    @Override
    public Optional<User> mapRow(ResultSet resultSet) {
        Optional<User> optionalUser;
        try {
            User user = new User.UserBuilder()
                    .id(resultSet.getLong("user_id"))
                    .email(resultSet.getString("email"))
                    .name(resultSet.getString("name"))
                    .surname(resultSet.getString("surname"))
                    .status(UserStatus.valueOf(resultSet.getString("user_status")))
                    .role(UserRole.valueOf(resultSet.getString("user_role")))
                    .phone(resultSet.getString("phone").replaceAll(PHONE_CODE_BY, ""))
                    .balance(resultSet.getBigDecimal("balance"))
                    .build();
            optionalUser = Optional.of(user);
        } catch (SQLException e){
            logger.error("Can not read resultset", e);
            optionalUser = Optional.empty();
        }
        return optionalUser;
    }
}
