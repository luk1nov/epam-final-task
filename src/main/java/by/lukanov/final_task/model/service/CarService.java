package by.lukanov.final_task.model.service;

import by.lukanov.final_task.entity.Car;
import by.lukanov.final_task.exception.ServiceException;

import java.util.List;

public interface CarService {

    List<Car> findAllCars() throws ServiceException;

}
