package by.lukyanov.finaltask.model.dao;

import by.lukyanov.finaltask.entity.AbstractEntity;
import by.lukyanov.finaltask.exception.DaoException;

import java.util.List;

public interface BaseDao<T extends AbstractEntity>{
    boolean delete(long id) throws DaoException;
    List<T> findAll(int from, int to) throws DaoException;
    boolean update(T t) throws DaoException;
}
