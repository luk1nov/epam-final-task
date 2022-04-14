package by.lukyanov.finaltask.model.dao;

import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.exception.DaoException;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface CarDao extends BaseDao<Car>{
    Optional<Car> findCarById(String id) throws DaoException;

    boolean insertWithImage(Car car, InputStream carImage) throws DaoException;

    boolean updateWithImage(Car car, InputStream carImage) throws DaoException;

    List<Car> findCarsByCategoryId(String id) throws DaoException;

    boolean changeCarActiveById(String id, boolean isActive) throws DaoException;
}