package by.lukyanov.finaltask.model.dao;

import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.exception.DaoException;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface CarDao extends BaseDao<Car>{
    Optional<Car> findCarById(long id) throws DaoException;

    boolean insert(Car car, InputStream carImage) throws DaoException;

    boolean updateWithImage(Car car, InputStream carImage) throws DaoException;

    List<Car> findCarsByCategoryId(long id) throws DaoException;

    boolean changeCarActiveById(long id, boolean isActive) throws DaoException;

    List<Car> findCarsByActive(boolean active, int limit, int offset) throws DaoException;

    int countAllCars() throws DaoException;

    int countAllCarsByActive(Boolean active) throws DaoException;
}