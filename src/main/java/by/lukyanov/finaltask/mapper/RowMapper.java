package by.lukyanov.finaltask.mapper;

import by.lukyanov.finaltask.entity.AbstractEntity;
import by.lukyanov.finaltask.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public interface RowMapper<T extends AbstractEntity> {
    Optional<T> mapRow(ResultSet rs) throws SQLException, DaoException;
}
