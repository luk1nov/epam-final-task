package by.lukyanov.final_task.model.dao;

import by.lukyanov.final_task.entity.AbstractEntity;
import by.lukyanov.final_task.exception.DaoException;

import java.util.List;

public interface BaseDao<T extends AbstractEntity>{
    boolean insert(T t) throws DaoException;
    boolean delete(T t) throws DaoException;
    List<T> findAll() throws DaoException;
    boolean update(T t) throws DaoException;
}
