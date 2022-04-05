package by.lukanov.final_task.model.service.impl;

import by.lukanov.final_task.entity.Car;
import by.lukanov.final_task.entity.User;
import by.lukanov.final_task.exception.DaoException;
import by.lukanov.final_task.exception.ServiceException;
import by.lukanov.final_task.model.dao.impl.CarDaoImpl;
import by.lukanov.final_task.model.dao.impl.UserDaoImpl;
import by.lukanov.final_task.model.service.CarService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CarServiceImpl implements CarService {
    private static final Logger logger = LogManager.getLogger();
    private static final CarDaoImpl carDao = CarDaoImpl.getInstance();

    @Override
    public List<Car> findAllCars() throws ServiceException {
        List<Car> cars;
        try {
            cars = carDao.findAll();
        } catch (DaoException e) {
            logger.error("Service exception trying find all cars");
            throw new ServiceException(e);
        }
        return cars;
    }
}
