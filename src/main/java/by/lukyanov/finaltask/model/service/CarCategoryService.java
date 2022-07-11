package by.lukyanov.finaltask.model.service;

import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The car category service interface.
 */
public interface CarCategoryService {
    /**
     * Creates new car category.
     *
     * @param title category title
     * @return true if category created, otherwise false
     * @throws ServiceException if there is a problem with car category service
     */
    boolean addCarCategory(String title) throws ServiceException;

    /**
     * Finds all car categories
     *
     * @return List of all car categories
     * @throws ServiceException if there is a problem with car category service
     */
    List<CarCategory> findAllCarCategories() throws ServiceException;

    /**
     * Finds car category with specified id.
     *
     * @param id category id
     * @return optional object of car category
     * @throws ServiceException if there is a problem with car category service
     */
    Optional<CarCategory> findCarCategoryById(String id) throws ServiceException;

    /**
     * Updates car category with specified id.
     *
     * @param id category id
     * @param title category title
     * @return true if title is valid and category updated, otherwise false
     * @throws ServiceException if there is a problem with car category service
     */
    boolean updateCarCategory(String id, String title) throws ServiceException;

    /**
     * Deletes car category. If there are cars, the category will change to default (id = 1).
     * If you need to change default category after delete, edit sql trigger.
     *
     * @param id category id
     * @return true if category deleted, otherwise false
     * @throws ServiceException if there is a problem with car category service
     */
    boolean deleteCarCategory(String id) throws ServiceException;

    /**
     * Finds car category with specified title.
     *
     * @param title category title
     * @return optional object of car category
     * @throws ServiceException if there is a problem with car category service
     */
    Optional<CarCategory> findCarCategoryByTitle(String title) throws ServiceException;
}
