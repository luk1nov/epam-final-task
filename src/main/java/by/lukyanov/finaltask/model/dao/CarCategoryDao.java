package by.lukyanov.finaltask.model.dao;

import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.exception.DaoException;

import java.util.Optional;

/**
 * The car category data access object interface
 */
public interface CarCategoryDao extends BaseDao<CarCategory> {
    /**
     * Creates new car category.
     *
     * @param category CarCategory object
     * @return true if category added, otherwise false
     * @throws DaoException if there is a problem with data access
     */
    boolean insert(CarCategory category) throws DaoException;

    /**
     * Finds car category with specified category id.
     *
     * @param id car category id
     * @return optional object of CarCategory
     * @throws DaoException if there is a problem with data access
     */
    Optional<CarCategory> findById(long id) throws DaoException;

    /**
     * Finds car category with specified category title.
     *
     * @param title car category title
     * @return optional object of CarCategory
     * @throws DaoException if there is a problem with data access
     */
    Optional<CarCategory> findCarCategoryByTitle(String title) throws DaoException;
}
