package by.lukyanov.finaltask.model.service.impl;

import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.entity.CarInfo;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.dao.impl.CarDaoImpl;
import by.lukyanov.finaltask.model.service.CarService;
import by.lukyanov.finaltask.util.ResultCounter;
import by.lukyanov.finaltask.validation.CarValidator;
import by.lukyanov.finaltask.validation.CommonValidator;
import by.lukyanov.finaltask.validation.impl.ValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class CarServiceImpl implements CarService {
    private static final Logger logger = LogManager.getLogger();
    private static final CommonValidator validator = ValidatorImpl.getInstance();
    private static final int DEFAULT_RESULT_PAGE = 1;
    private static final CarServiceImpl instance = new CarServiceImpl();
    private CarDaoImpl carDao;

    private CarServiceImpl() {
        carDao = CarDaoImpl.getInstance();
    }

    public static CarServiceImpl getInstance(){
        return instance;
    }

    @Override
    public boolean addCar(Map<String, String> carData, InputStream carImage) throws ServiceException {
        boolean result = false;
        CarValidator carValidator = ValidatorImpl.getInstance();

        String brand = carData.get(CAR_BRAND);
        String model = carData.get(CAR_MODEL);
        String vinCode = carData.get(CAR_VIN_CODE);
        String regularPrice = carData.get(CAR_REGULAR_PRICE);
        String isActive = carData.get(CAR_ACTIVE);
        String acceleration = carData.get(CAR_INFO_ACCELERATION);
        String categoryId = carData.get(CAR_CATEGORY_ID);
        String power = carData.get(CAR_INFO_POWER);
        String drivetrain = carData.get(CAR_INFO_DRIVETRAIN);
        Optional<String> salePrice = Optional.ofNullable(carData.get(CAR_SALE_PRICE));

        if (validator.isOneWord(brand) && carValidator.isValidCarModel(model) && carValidator.isValidVinCode(vinCode) &&
                validator.isValidPrice(regularPrice) && carValidator.isValidAcceleration(acceleration) && carValidator.isValidPower(power) &&
                validator.isValidId(categoryId) && (salePrice.isEmpty() || validator.isValidPrice(salePrice.get()) && comparePrices(regularPrice, salePrice.get()))){
            try {
                CarInfo carInfo = new CarInfo(Double.parseDouble(acceleration), Integer.parseInt(power), CarInfo.Drivetrain.valueOf(drivetrain.toUpperCase()));
                Car car = new Car.CarBuilder()
                        .brand(brand)
                        .model(model)
                        .vin(vinCode)
                        .regularPrice(new BigDecimal(regularPrice))
                        .salePrice(salePrice.map(BigDecimal::new).orElse(null))
                        .active(Boolean.parseBoolean(isActive))
                        .category(new CarCategory(Long.parseLong(categoryId)))
                        .carInfo(carInfo)
                        .build();
                result = carDao.insert(car, carImage);
            } catch (DaoException | IllegalArgumentException e) {
                logger.error("Service exception trying add car", e);
                throw new ServiceException(e);
            }
        } else {
            logger.info("Provided invalid car data");
        }
        return result;
    }

    @Override
    public List<Car> findAllCars(String pageNumber, int postsPerPage) throws ServiceException {
        List<Car> cars;
        try {
            int carsPage = validator.isValidNumber(pageNumber) ? Integer.parseInt(pageNumber) : DEFAULT_RESULT_PAGE;
            ResultCounter counter = new ResultCounter(carsPage, postsPerPage);
            cars = carDao.findAll(postsPerPage, counter.offset());
        } catch (DaoException e) {
            logger.error("Service exception trying find all cars", e);
            throw new ServiceException(e);
        }
        return cars;
    }

    @Override
    public Optional<Car> findCarById(String id) throws ServiceException {
        Optional<Car> car;
        if (validator.isValidId(id)){
            try {
                car = carDao.findCarById(Long.parseLong(id));
            } catch (DaoException e) {
                logger.error("Service exception trying find car by id", e);
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
        CarValidator carValidator = ValidatorImpl.getInstance();

        String carId = carData.get(CAR_ID);
        String brand = carData.get(CAR_BRAND);
        String model = carData.get(CAR_MODEL);
        String vinCode = carData.get(CAR_VIN_CODE);
        String regularPrice = carData.get(CAR_REGULAR_PRICE);
        String isActive = carData.get(CAR_ACTIVE);
        String categoryId = carData.get(CAR_CATEGORY_ID);
        String acceleration = carData.get(CAR_INFO_ACCELERATION);
        String power = carData.get(CAR_INFO_POWER);
        String drivetrain = carData.get(CAR_INFO_DRIVETRAIN);
        Optional<String> salePrice = Optional.ofNullable(carData.get(CAR_SALE_PRICE));
        String changeImg = carData.get(UPLOAD_IMAGE);

        if (validator.isOneWord(brand) && carValidator.isValidCarModel(model) && carValidator.isValidVinCode(vinCode) &&
                validator.isValidPrice(regularPrice) && carValidator.isValidAcceleration(acceleration) && carValidator.isValidPower(power) &&
                validator.isValidId(categoryId) && (salePrice.isEmpty() || validator.isValidPrice(salePrice.get()) && comparePrices(regularPrice, salePrice.get()))){
            try {
                CarInfo carInfo = new CarInfo(Double.parseDouble(acceleration), Integer.parseInt(power), CarInfo.Drivetrain.valueOf(drivetrain.toUpperCase()));
                Car car = new Car.CarBuilder()
                        .id(Long.valueOf(carId))
                        .brand(brand)
                        .model(model)
                        .vin(vinCode)
                        .regularPrice(new BigDecimal(regularPrice))
                        .salePrice(salePrice.map(BigDecimal::new).orElse(null))
                        .active(Boolean.parseBoolean(isActive))
                        .category(new CarCategory(Long.parseLong(categoryId)))
                        .carInfo(carInfo)
                        .build();
                result = Boolean.parseBoolean(changeImg) ? carDao.updateWithImage(car, carImage) : carDao.update(car);
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
    public List<Car> findCarsByCategoryId(String id, String pageNumber, int postsPerPage) throws ServiceException {
        List<Car> cars = new ArrayList<>();
        if (validator.isValidId(id)) {
            try {
                int carsPage = validator.isValidNumber(pageNumber) ? Integer.parseInt(pageNumber) : DEFAULT_RESULT_PAGE;
                ResultCounter counter = new ResultCounter(carsPage, postsPerPage);
                cars = carDao.findCarsByCategoryId(Long.parseLong(id), postsPerPage, counter.offset());
            } catch (DaoException e) {
                logger.error("Service exception trying find cars by category id", e);
                throw new ServiceException(e);
            }
        }
        return cars;
    }

    @Override
    public boolean deleteCarById(String carId) throws ServiceException {
        boolean result = false;
        if(validator.isValidId(carId)){
            try {
                result = carDao.delete(Long.parseLong(carId));
            } catch (DaoException e) {
                logger.error("Service exception trying delete car", e);
                throw new ServiceException(e);
            }
        } else {
            logger.info("Provided invalid carId in deleteCarById method");
        }
        return result;
    }

    @Override
    public boolean changeCarActive(String carId, String active) throws ServiceException {
        boolean result = false;
        if(validator.isValidId(carId)){
            boolean changedActiveStatus = Boolean.parseBoolean(active);
            try {
                result = carDao.changeCarActiveById(Long.parseLong(carId), !changedActiveStatus);
            } catch (DaoException e) {
                logger.error("Service exception trying change car active status", e);
                throw new ServiceException(e);
            }
        } else {
            logger.info("Provided invalid carId in changeCarActive method");
        }
        return result;
    }

    @Override
    public List<Car> findCarsByActiveStatus(boolean active, String pageNumber, int postsPerPage) throws ServiceException {
        List<Car> cars;
        try {
            int carsPage = validator.isValidNumber(pageNumber) ? Integer.parseInt(pageNumber) : DEFAULT_RESULT_PAGE;
            ResultCounter counter = new ResultCounter(carsPage, postsPerPage);
            cars = carDao.findCarsByActive(active, postsPerPage, counter.offset());
        } catch (DaoException e) {
            logger.error("Service exception trying find cars by active", e);
            throw new ServiceException(e);
        }
        return cars;
    }

    @Override
    public int countAllCars() throws ServiceException {
        try {
            return carDao.countAllCars();
        } catch (DaoException e) {
            logger.error("Service exception trying count all cars", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public int countAllCarsByActive(Boolean active) throws ServiceException {
        try {
            return carDao.countAllCarsByActive(active);
        } catch (DaoException e) {
            logger.error("Service exception trying count all cars by active", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public int countAllCarsByCategoryId(long categoryId) throws ServiceException {
        try {
            return carDao.countAllCarsByCategoryId(categoryId);
        } catch (DaoException e) {
            logger.error("Service exception trying count all cars by active", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Car> searchCars(String searchQuery) throws ServiceException {
        List<Car> cars = new ArrayList<>();
        try {
            if (validator.isValidSearchQuery(searchQuery)){
                cars = carDao.searchCars(searchQuery.strip());
            }
        } catch (DaoException e) {
            logger.error("Service exception trying search cars", e);
            throw new ServiceException(e);
        }
        return cars;
    }

    @Override
    public Optional<Car> findCarByVinCode(String vinCode) throws ServiceException {
        CarValidator carValidator = ValidatorImpl.getInstance();
        Optional<Car> car = Optional.empty();
        if (carValidator.isValidVinCode(vinCode)){
            try {
                car = carDao.findCarByVinCode(vinCode);
            } catch (DaoException e) {
                logger.error("Service exception trying find car by vin code", e);
                throw new ServiceException(e);
            }
        }
        return car;
    }

    private boolean comparePrices(String regularPrice, String salePrice){
        BigDecimal decimalSalePrice = new BigDecimal(salePrice);
        BigDecimal decimalRegularPrice = new BigDecimal(regularPrice);
        if(decimalSalePrice.compareTo(decimalRegularPrice) < 0){
            return true;
        }
        logger.info("sale price equals or greater than regular");
        return false;
    }

}
