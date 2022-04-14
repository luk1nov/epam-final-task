package by.lukyanov.finaltask.model.service.impl;

import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.dao.impl.CarCategoryDaoImpl;
import by.lukyanov.finaltask.model.service.CarCategoryService;
import by.lukyanov.finaltask.validation.impl.ValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class CarCategoryServiceImpl implements CarCategoryService {
    private static final Logger logger = LogManager.getLogger();
    private static final CarCategoryDaoImpl carCategoryDao = CarCategoryDaoImpl.getInstance();
    private static final ValidatorImpl validator = ValidatorImpl.getInstance();
    private static final String DEFAULT_CAR_CATEGORY_ID = "1";


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
            carCategories = carCategoryDao.findAll();
        } catch (DaoException e) {
            logger.error("Service exception trying find all users");
            throw new ServiceException(e);
        }
        return carCategories;
    }

    @Override
    public Optional<CarCategory> findCarCategoryById(String id) throws ServiceException {
        Optional<CarCategory> optionalCarCategory;
        if(validator.isValidId(id)){
            try {
                optionalCarCategory = carCategoryDao.findById(id);
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
                result = carCategoryDao.delete(id);
            } catch (DaoException e) {
                logger.error("Service exception trying delete car category", e);
                throw new ServiceException(e);
            }
        } else{
            logger.info("provided invalid car category data");
        }
        return result;
    }
}
