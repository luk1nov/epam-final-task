package by.lukyanov.finaltask.model.dao.mapper;

import by.lukyanov.finaltask.entity.AbstractEntity;

import java.sql.ResultSet;
import java.util.Optional;

/**
 * Result set row mapper
 * @param <T> AbstractEntity's child class
 */
public interface RowMapper<T extends AbstractEntity> {
    /**
     * @param rs result set
     * @return optional object of AbstractEntity's child
     */
    Optional<T> mapRow(ResultSet rs);
}
