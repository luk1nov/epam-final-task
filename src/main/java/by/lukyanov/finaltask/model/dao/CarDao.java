package by.lukyanov.finaltask.model.dao;

import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.exception.DaoException;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * The car data access object interface
 */
public interface CarDao extends BaseDao<Car>{
    /**
     * Finds car with specified car id.
     *
     * @param id car id
     * @return optional object of Car
     * @throws DaoException if there is a problem with data access
     */
    Optional<Car> findCarById(long id) throws DaoException;

    /**
     * Creates new car.
     *
     * @param car Car object
     * @param carImage InputStream of car image
     * @return true if car added, otherwise false
     * @throws DaoException if there is a problem with data access
     */
    boolean insert(Car car, InputStream carImage) throws DaoException;

    /**
     * Updates car with image.
     *
     * @param car Car object
     * @param carImage  InputStream of car image
     * @return true if car updated, otherwise false
     * @throws DaoException if there is a problem with data access
     */
    boolean updateWithImage(Car car, InputStream carImage) throws DaoException;

    /**
     * Finds cars of specified category id for the current page.
     *
     * @param id car category id
     * @param limit requested row limit
     * @param offset from which row to get the result
     * @return List of cars from requested category
     * @throws DaoException if there is a problem with data access
     */
    List<Car> findCarsByCategoryId(long id, int limit, int offset) throws DaoException;

    /**
     * Changes car activity with specified car id.
     *
     * @param id car id
     * @param isActive car activity
     * @return true if car activity changed, otherwise false
     * @throws DaoException if there is a problem with data access
     */
    boolean changeCarActiveById(long id, boolean isActive) throws DaoException;

    /**
     * Finds cars with specified activity for the current page.
     *
     * @param active car activity
     * @param limit requested row limit
     * @param offset from which row to get the result
     * @return List of cars with requested activity
     * @throws DaoException if there is a problem with data access
     */
    List<Car> findCarsByActive(boolean active, int limit, int offset) throws DaoException;

    /**
     * Counts all cars.
     *
     * @return number of all cars
     * @throws DaoException if there is a problem with data access
     */
    int countAllCars() throws DaoException;

    /**
     * Counts all cars with specified car activity.
     *
     * @param active car activity
     * @return number of cars with requested activity
     * @throws DaoException if there is a problem with data access
     */
    int countAllCarsByActive(Boolean active) throws DaoException;

    /**
     * Counts all cars of specified car category id.
     *
     * @param categoryId car category id
     * @return number of cars from requested car category
     * @throws DaoException if there is a problem with data access
     */
    int countAllCarsByCategoryId(long categoryId) throws DaoException;

    /**
     * Searches cars by brand, model and vin code.
     *
     * @param searchQuery search query
     * @return List of cars where brand, model or vin code contains search query
     * @throws DaoException if there is a problem with data access
     */
    List<Car> searchCars(String searchQuery) throws DaoException;

    /**
     * Finds car with specified vin code.
     *
     * @param vinCode vin code
     * @return optional object of Car
     * @throws DaoException if there is a problem with data access
     */
    Optional<Car> findCarByVinCode(String vinCode) throws DaoException;
}