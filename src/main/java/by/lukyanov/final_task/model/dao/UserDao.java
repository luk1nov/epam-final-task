package by.lukyanov.final_task.model.dao;

import by.lukyanov.final_task.entity.User;
import by.lukyanov.final_task.exception.DaoException;

import java.util.Optional;

public interface UserDao extends BaseDao<User>{
    Optional<User> authenticate(String email, String password) throws DaoException;

    Optional<User> findUserByEmail(String email) throws DaoException;

    Optional<User> findUserById(String id) throws DaoException;
}
