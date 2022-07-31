package by.lukyanov.finaltask.model.service.impl;

import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.connection.ConnectionPool;
import by.lukyanov.finaltask.model.dao.impl.CarCategoryDaoImpl;
import by.lukyanov.finaltask.model.service.CarCategoryService;
import by.lukyanov.finaltask.util.ResultCounter;
import by.lukyanov.finaltask.validation.CarValidator;
import by.lukyanov.finaltask.validation.CommonValidator;
import by.lukyanov.finaltask.validation.impl.ValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class CarCategoryServiceImpl implements CarCategoryService {
    private static final Logger logger = LogManager.getLogger();
    private static final CommonValidator validator = ValidatorImpl.getInstance();
    private static final String DEFAULT_CAR_CATEGORY_ID = "1";
    private static final CarCategoryServiceImpl instance = new CarCategoryServiceImpl();
    private CarCategoryDaoImpl carCategoryDao;

    private CarCategoryServiceImpl() {
        carCategoryDao = CarCategoryDaoImpl.getInstance();
    }

    static public CarCategoryServiceImpl getInstance(){
        return instance;
    }

    @Override
    public boolean addCarCategory(String title) throws ServiceException {
        boolean result = false;
        if(validator.isOneWord(title)){
            try {
                CarCategory category = new CarCategory(title);
                result = carCategoryDao.insert(category);
            } catch (DaoException e) {
                logger.error("Service exception trying add new car category", e);
                throw new ServiceException(e);
            }
        } else{
            logger.info("Provided invalid car category data");
        }
        return result;
    }

    @Override
    public List<CarCategory> findAllCarCategories() throws ServiceException {
        List<CarCategory> carCategories;
        try {
            carCategories = carCategoryDao.findAll(Integer.MAX_VALUE, 0);
        } catch (DaoException e) {
            logger.error("Service exception trying find all users", e);
            throw new ServiceException(e);
        }
        return carCategories;
    }

    @Override
    public Optional<CarCategory> findCarCategoryById(String id) throws ServiceException {
        Optional<CarCategory> optionalCarCategory;
        if(validator.isValidId(id)){
            try {
                optionalCarCategory = carCategoryDao.findById(Long.parseLong(id));
            } catch (DaoException e) {
                logger.error("Service exception trying update car category", e);
                throw new ServiceException(e);
            }
        } else {
            logger.info("provided invalid data");
            optionalCarCategory = Optional.empty();
        }
        return optionalCarCategory;
    }


    public boolean updateCarCategory(String id, String title) throws ServiceException {
        boolean result = false;
        if(validator.isValidId(id) && validator.isOneWord(title)){
            try {
                CarCategory category = new CarCategory(Long.parseLong(id), title);
                result = carCategoryDao.update(category);
            } catch (DaoException e) {
                logger.error("Service exception trying update car category", e);
                throw new ServiceException(e);
            }
        } else {
            logger.info("provided invalid data");
        }
        return result;
    }

    @Override
    public boolean deleteCarCategory(String id) throws ServiceException {
        boolean result = false;
        if (validator.isValidId(id) && !id.equals(DEFAULT_CAR_CATEGORY_ID)){
            try {
                result = carCategoryDao.delete(Long.parseLong(id));
            } catch (DaoException e) {
                logger.error("Service exception trying delete car category", e);
                throw new ServiceException(e);
            }
        } else{
            logger.info("provided invalid car category data");
        }
        return result;
    }

    @Override
    public Optional<CarCategory> findCarCategoryByTitle(String title) throws ServiceException {
        Optional<CarCategory> optionalCarCategory = Optional.empty();
        if(validator.isOneWord(title)){
            try {
                optionalCarCategory = carCategoryDao.findCarCategoryByTitle(title.strip());
            } catch (DaoException e) {
                logger.error("Service exception trying find car category by title", e);
                throw new ServiceException(e);
            }
        }
        return optionalCarCategory;
    }
}
