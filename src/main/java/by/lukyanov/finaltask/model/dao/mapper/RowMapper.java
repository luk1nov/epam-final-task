package by.lukyanov.finaltask.model.dao.mapper;

import by.lukyanov.finaltask.entity.AbstractEntity;

import java.sql.ResultSet;
import java.util.Optional;

public interface RowMapper<T extends AbstractEntity> {
    Optional<T> mapRow(ResultSet rs);
}
