package by.lukyanov.finaltask.model.service.impl;

import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.entity.CarInfo;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.dao.impl.CarDaoImpl;
import by.lukyanov.finaltask.model.service.CarService;
import by.lukyanov.finaltask.validation.impl.ValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.lukyanov.finaltask.command.ParameterAndAttribute.*;

public class CarServiceImpl implements CarService {
    private static final Logger logger = LogManager.getLogger();
    private static final CarDaoImpl carDao = CarDaoImpl.getInstance();
    private static final ValidatorImpl validator = ValidatorImpl.getInstance();

    @Override
    public boolean addCar(Map<String, String> carData, InputStream carImage) throws ServiceException {
        boolean result = false;
        String brand = carData.get(CAR_BRAND);
        String model = carData.get(CAR_MODEL);
        String regularPrice = carData.get(CAR_REGULAR_PRICE);
        String isActive = carData.get(CAR_ACTIVE);
        String acceleration = carData.get(CAR_INFO_ACCELERATION);
        String power = carData.get(CAR_INFO_POWER);
        String drivetrain = carData.get(CAR_INFO_DRIVETRAIN);
        Optional<String> salePrice = Optional.ofNullable(carData.get(CAR_SALE_PRICE));

        if (validator.isOneWord(brand) && validator.isValidCarModel(model) && validator.isValidPrice(regularPrice) &&
                validator.isValidCarActive(isActive) && validator.isValidAcceleration(acceleration) && validator.isValidPower(power) &&
                (salePrice.isEmpty() || validator.isValidPrice(salePrice.get()))){
            if (salePrice.isPresent()){
                BigDecimal decimalSalePrice = new BigDecimal(salePrice.get());
                BigDecimal decimalRegularPrice = new BigDecimal(regularPrice);
                if(decimalSalePrice.compareTo(decimalRegularPrice) != -1){
                    logger.info("sale price greater than regular");
                    return false;
                }
            }
            CarInfo carInfo = new CarInfo(Double.parseDouble(acceleration), Integer.parseInt(power), CarInfo.Drivetrain.valueOf(drivetrain.toUpperCase()));
            Car car = new Car.CarBuilder()
                    .brand(brand)
                    .model(model)
                    .regularPrice(new BigDecimal(regularPrice))
                    .salePrice(salePrice.isPresent() ? BigDecimal.valueOf(Double.parseDouble(salePrice.get())) : null)
                    .active(Boolean.parseBoolean(isActive))
                    .carInfo(carInfo)
                    .build();
            try {
                if(carImage != null){
                    result = carDao.insertWithImage(car, carImage);
                } else {
                    result = carDao.insert(car);
                }
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

    @Override
    public Optional<Car> findCarById(String id) throws ServiceException {
        Optional<Car> car;
        if (validator.isValidId(id)){
            try {
                car = carDao.findCarById(id);
            } catch (DaoException e) {
                logger.error("Service exception trying find car by id");
                throw new ServiceException(e);
            }
        } else {
            car = Optional.empty();
        }
        return car;
    }

    @Override
    public boolean updateCar(Map<String, String> carData, InputStream carImage) throws ServiceException {
        boolean result = false;
        String carId = carData.get(CAR_ID);
        String brand = carData.get(CAR_BRAND);
        String model = carData.get(CAR_MODEL);
        String regularPrice = carData.get(CAR_REGULAR_PRICE);
        String isActive = carData.get(CAR_ACTIVE);
        String acceleration = carData.get(CAR_INFO_ACCELERATION);
        String power = carData.get(CAR_INFO_POWER);
        String drivetrain = carData.get(CAR_INFO_DRIVETRAIN);
        Optional<String> salePrice = Optional.ofNullable(carData.get(CAR_SALE_PRICE));
        String changeImg = carData.get(CHANGE_IMAGE);

        if (validator.isOneWord(brand) && validator.isValidCarModel(model) && validator.isValidPrice(regularPrice) &&
                validator.isValidCarActive(isActive) && validator.isValidAcceleration(acceleration) && validator.isValidPower(power) &&
                (salePrice.isEmpty() || validator.isValidPrice(salePrice.get()))){
            if (salePrice.isPresent()){
                BigDecimal decimalSalePrice = new BigDecimal(salePrice.get());
                BigDecimal decimalRegularPrice = new BigDecimal(regularPrice);
                if(decimalSalePrice.compareTo(decimalRegularPrice) != -1){
                    logger.info("sale price greater than regular");
                    return false;
                }
            }
            try {
                CarInfo carInfo = new CarInfo(Double.parseDouble(acceleration), Integer.parseInt(power), CarInfo.Drivetrain.valueOf(drivetrain.toUpperCase()));
                Car car = new Car.CarBuilder()
                        .id(Long.valueOf(carId))
                        .brand(brand)
                        .model(model)
                        .regularPrice(new BigDecimal(regularPrice))
                        .salePrice(salePrice.isPresent() ? BigDecimal.valueOf(Double.parseDouble(salePrice.get())) : null)
                        .active(Boolean.parseBoolean(isActive))
                        .carInfo(carInfo)
                        .build();
                if(changeImg.equalsIgnoreCase("true")){
                    result = carDao.updateWithImage(car, carImage);
                } else {
                    result = carDao.update(car);
                }
            } catch (DaoException e) {
                logger.error("Service exception trying edit car", e);
                throw new ServiceException(e);
            }
        } else {
            logger.info("Provided invalid car data");
        }
        return result;
    }

    @Override
    public List<Car> findCarsByCategoryId(String id) throws ServiceException {
        List<Car> cars;
        if (validator.isValidId(id)) {
            try {
                cars = carDao.findCarsByCategoryId(id);
            } catch (DaoException e) {
                logger.error("Service exception trying find cars by category id");
                throw new ServiceException(e);
            }
        } else {
            cars = new ArrayList<>();
        }
        return cars;
    }

}
