package by.lukyanov.finaltask.model.service;

import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.exception.ServiceException;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CarService {

    boolean addCar(Map<String, String> carData, InputStream carImage) throws ServiceException;

    List<Car> findAllCars(String pageNumber, int postsPerPage) throws ServiceException;

    Optional<Car> findCarById(String id) throws ServiceException;

    boolean updateCar(Map<String, String> carData, InputStream carImage) throws ServiceException;

    List<Car> findCarsByCategoryId(String id, String pageNumber, int postsPerPage) throws ServiceException;

    boolean deleteCarById(String carId) throws ServiceException;

    boolean changeCarActive(String carId, String active) throws ServiceException;

    List<Car> findCarsByActiveStatus(boolean active, String pageNumber, int postsPerPage) throws ServiceException;

    int countAllCars() throws ServiceException;

    int countAllCarsByActive(Boolean active) throws ServiceException;

    int countAllCarsByCategoryId(long categoryId) throws ServiceException;

    List<Car> searchCars(String searchQuery) throws ServiceException;
}
