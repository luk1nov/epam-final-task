package by.lukyanov.final_task.model.service.impl;

import static by.lukyanov.final_task.command.ParameterAndAttribute.*;
import by.lukyanov.final_task.entity.Car;
import by.lukyanov.final_task.entity.CarInfo;
import by.lukyanov.final_task.exception.DaoException;
import by.lukyanov.final_task.exception.ServiceException;
import by.lukyanov.final_task.model.dao.impl.CarDaoImpl;
import by.lukyanov.final_task.model.service.CarService;
import static by.lukyanov.final_task.validation.impl.ValidatorImpl.*;

import by.lukyanov.final_task.validation.impl.ValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CarServiceImpl implements CarService {
    private static final Logger logger = LogManager.getLogger();
    private static final CarDaoImpl carDao = CarDaoImpl.getInstance();
    private static final ValidatorImpl validator = ValidatorImpl.getInstance();

    @Override
    public boolean addCar(Map<String, String> carData) throws ServiceException {
        boolean result = false;
        String brand = carData.get(CAR_BRAND);
        String model = carData.get(CAR_MODEL);
        String regularPrice = carData.get(CAR_REGULAR_PRICE);
        String isActive = carData.get(CAR_ACTIVE);
        String acceleration = carData.get(CAR_INFO_ACCELERATION);
        String power = carData.get(CAR_INFO_POWER);
        String drivetrain = carData.get(CAR_INFO_DRIVETRAIN);
        Optional<String> salePrice = Optional.ofNullable(carData.get(CAR_SALE_PRICE));
        logger.info(salePrice);
        if (validator.isOneWord(brand) && validator.isValidCarModel(model) && validator.isValidPrice(regularPrice) &&
                validator.isValidCarActive(isActive) && validator.isValidAcceleration(acceleration) && validator.isValidPower(power) &&
                (salePrice.isEmpty() || validator.isValidPrice(salePrice.get()))){
            CarInfo carInfo = new CarInfo(Double.parseDouble(acceleration), Integer.parseInt(power), CarInfo.Drivetrain.valueOf(drivetrain.toUpperCase()));
            Car car = new Car.CarBuilder()
                    .brand(brand)
                    .model(model)
                    .regularPrice(BigDecimal.valueOf(Double.parseDouble(regularPrice)))
                    .salePrice(salePrice.isPresent() ? BigDecimal.valueOf(Double.parseDouble(salePrice.get())) : null)
                    .active(Boolean.parseBoolean(isActive))
                    .carInfo(carInfo)
                    .build();
            try {
                result = carDao.insert(car);
            } catch (DaoException e) {
                logger.error("Service exception trying add car", e);
                throw new ServiceException(e);
            }
        } else {
            logger.info("Provided invalid car data");
        }
        return result;
    }

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
