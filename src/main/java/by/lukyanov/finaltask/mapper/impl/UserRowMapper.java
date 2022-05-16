package by.lukyanov.finaltask.mapper.impl;

import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.mapper.RowMapper;

import java.sql.ResultSet;
import java.util.Optional;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public Optional<User> mapRow(ResultSet rs) {
        return Optional.empty();
    }
}
