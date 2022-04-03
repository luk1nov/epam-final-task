package by.lukanov.final_task.dao;

import by.lukanov.final_task.entity.AbstractEntity;
import by.lukanov.final_task.exception.DaoException;

import java.util.List;

public interface BaseDao<T extends AbstractEntity>{
    boolean insert(T t) throws DaoException;
    boolean delete(T t);
    List<T> findAllUsers() throws DaoException;
    boolean update(T t) throws DaoException;
}
