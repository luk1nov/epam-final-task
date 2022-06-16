package by.lukyanov.finaltask.model.service;

import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface CarCategoryService {
    boolean addCarCategory(String title) throws ServiceException;

    List<CarCategory> findAllCarCategories() throws ServiceException;

    Optional<CarCategory> findCarCategoryById(String id) throws ServiceException;

    boolean updateCarCategory(String id, String title) throws ServiceException;

    boolean deleteCarCategory(String id) throws ServiceException;

    Optional<CarCategory> findCarCategoryByTitle(String title) throws ServiceException;
}
