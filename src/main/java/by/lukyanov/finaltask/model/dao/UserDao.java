package by.lukyanov.finaltask.model.dao;

import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.DaoException;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserDao extends BaseDao<User>{
    Optional<User> authenticate(String email, String password) throws DaoException;

    Optional<User> findUserByEmail(String email) throws DaoException;

    Optional<User> findUserById(String id) throws DaoException;

    boolean refillBalance(long id, BigDecimal amount) throws DaoException;
}
