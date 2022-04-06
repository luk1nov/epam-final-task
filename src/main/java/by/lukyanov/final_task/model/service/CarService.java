package by.lukyanov.final_task.model.service;

import by.lukyanov.final_task.entity.Car;
import by.lukyanov.final_task.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface CarService {

    boolean addCar(Map<String, String> carData) throws ServiceException;

    List<Car> findAllCars() throws ServiceException;

}
