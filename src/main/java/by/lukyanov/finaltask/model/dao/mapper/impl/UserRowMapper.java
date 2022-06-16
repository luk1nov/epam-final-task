package by.lukyanov.finaltask.model.dao.mapper.impl;

import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.entity.UserRole;
import by.lukyanov.finaltask.entity.UserStatus;
import by.lukyanov.finaltask.model.dao.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static by.lukyanov.finaltask.model.dao.ColumnName.*;

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
                    .id(resultSet.getLong(USER_ID))
                    .email(resultSet.getString(USER_EMAIL))
                    .name(resultSet.getString(USER_NAME))
                    .surname(resultSet.getString(USER_SURNAME))
                    .status(UserStatus.valueOf(resultSet.getString(USER_STATUS)))
                    .role(UserRole.valueOf(resultSet.getString(USER_ROLE)))
                    .phone(resultSet.getString(USER_PHONE).replaceAll(PHONE_CODE_BY, ""))
                    .balance(resultSet.getBigDecimal(USER_BALANCE))
                    .build();
            optionalUser = Optional.of(user);
        } catch (SQLException e){
            logger.error("Can not read resultset", e);
            optionalUser = Optional.empty();
        }
        return optionalUser;
    }
}
