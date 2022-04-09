package by.lukyanov.finaltask.model.service;

import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CarService {

    boolean addCar(Map<String, String> carData) throws ServiceException;

    List<Car> findAllCars() throws ServiceException;

    Optional<Car> findCarById(String id) throws ServiceException;

    boolean updateCar(Map<String, String> carData) throws ServiceException;


}
