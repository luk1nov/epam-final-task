package by.lukyanov.finaltask.model.dao;

import by.lukyanov.finaltask.entity.AbstractEntity;
import by.lukyanov.finaltask.exception.DaoException;

import java.util.List;


/**
 * Base data access object interface
 * @param <T> AbstractEntity's child class
 */
public interface BaseDao<T extends AbstractEntity>{

    /**
     * Deletes Entity with specified id.
     *
     * @param id abstract entity's child class id
     * @return true if row deleted, otherwise false
     * @throws DaoException if there is a problem with data access
     */
    boolean delete(long id) throws DaoException;

    /**
     * Finds all entity's for current page.
     *
     * @param limit requested row limit
     * @param offset from which row to get the result
     * @return List of all rows
     * @throws DaoException if there is a problem with data access
     */
    List<T> findAll(int limit, int offset) throws DaoException;

    /**
     * Updates entity.
     *
     * @param t abstract entity's child
     * @return true if row updated, otherwise false
     * @throws DaoException if there is a problem with data access
     */
    boolean update(T t) throws DaoException;
}
