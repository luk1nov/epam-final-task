package by.lukyanov.finaltask.model.dao;

import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.DaoException;

import java.util.Optional;

public interface CarDao extends BaseDao<Car>{
    Optional<Car> findCarById(String id) throws DaoException;

}