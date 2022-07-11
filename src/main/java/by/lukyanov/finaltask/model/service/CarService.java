package by.lukyanov.finaltask.model.service;

import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.exception.ServiceException;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The car service interface.
 */
public interface CarService {
    /**
     * Creates new car.
     *
     * @param carData Map of car data
     * @param carImage InputStream of car image
     * @return true if car data is valid and car added, otherwise false
     * @throws ServiceException if there is a problem with car service
     */
    boolean addCar(Map<String, String> carData, InputStream carImage) throws ServiceException;

    /**
     * Finds all cars for requested page.
     *
     * @param pageNumber requested number of page
     * @param postsPerPage number of posts per page
     * @return List of cars for requested page
     * @throws ServiceException if there is a problem with car service
     */
    List<Car> findAllCars(String pageNumber, int postsPerPage) throws ServiceException;

    /**
     * Finds car with specified id.
     *
     * @param id car id
     * @return optional object of car
     * @throws ServiceException if there is a problem with car service
     */
    Optional<Car> findCarById(String id) throws ServiceException;

    /**
     * Updates car.
     *
     * @param carData Map of car data
     * @param carImage InputStream of car image
     * @return true if car updated, otherwise false
     * @throws ServiceException if there is a problem with car service
     */
    boolean updateCar(Map<String, String> carData, InputStream carImage) throws ServiceException;

    /**
     * Finds all cars of specified category for requested page.
     *
     * @param id car id
     * @param pageNumber requested number of page
     * @param postsPerPage number of posts per page
     * @return List of cars of specified category for requested page
     * @throws ServiceException if there is a problem with car service
     */
    List<Car> findCarsByCategoryId(String id, String pageNumber, int postsPerPage) throws ServiceException;

    /**
     * Deletes car, if it is not used in active or processing orders.
     *
     * @param carId car id
     * @return true if car deleted, otherwise false
     * @throws ServiceException if there is a problem with car service
     */
    boolean deleteCarById(String carId) throws ServiceException;

    /**
     * Changes car activity with specified id.
     * 
     * @param carId car id
     * @param active "true" - active, otherwise - repair
     * @return true if activity changed, otherwise false
     * @throws ServiceException if there is a problem with car service
     */
    boolean changeCarActive(String carId, String active) throws ServiceException;

    /**
     * Finds car with specified activity for requested page.
     * 
     * @param active true - active cars; false - cars on repair
     * @param pageNumber requested number of page
     * @param postsPerPage number of posts per page
     * @return List of cars with specified activity for requested page
     * @throws ServiceException if there is a problem with car service
     */
    List<Car> findCarsByActiveStatus(boolean active, String pageNumber, int postsPerPage) throws ServiceException;

    /**
     * Counts all cars.
     * 
     * @return number of all cars
     * @throws ServiceException if there is a problem with car service
     */
    int countAllCars() throws ServiceException;

    /**
     * Counts all cars with specified activity.
     * 
     * @param active true - active cars; false - cars on repair
     * @return number of cars with specified activity
     * @throws ServiceException if there is a problem with car service
     */
    int countAllCarsByActive(Boolean active) throws ServiceException;

    /**
     * Counts all cars of specified category.
     * 
     * @param categoryId category id
     * @return number of cars of specified category
     * @throws ServiceException if there is a problem with car service
     */
    int countAllCarsByCategoryId(long categoryId) throws ServiceException;

    /**
     * Searches cars by brand, model and vin code. 
     * 
     * @param searchQuery search query
     * @return List of cars where brand, model or vin code contains search query
     * @throws ServiceException if there is a problem with car service
     */
    List<Car> searchCars(String searchQuery) throws ServiceException;

    /**
     * Finds car with specified vin code.
     * 
     * @param vinCode vin code
     * @return optional object of car
     * @throws ServiceException if there is a problem with car service
     */
    Optional<Car> findCarByVinCode(String vinCode) throws ServiceException;
}
