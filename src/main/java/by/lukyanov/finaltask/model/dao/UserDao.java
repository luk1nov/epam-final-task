package by.lukyanov.finaltask.model.dao;

import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.entity.UserStatus;
import by.lukyanov.finaltask.exception.DaoException;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<User>{
    Optional<User> authenticate(String email, String password) throws DaoException;

    Optional<User> findUserByEmail(String email) throws DaoException;

    Optional<User> findUserById(long id) throws DaoException;

    boolean updateBalance(long id, BigDecimal amount, boolean subtract) throws DaoException;

    boolean updateUserInfo(User user) throws DaoException;

    boolean updateDriverLicense(long id, InputStream image) throws DaoException;

    boolean updatePassword(long id, String password) throws DaoException;

    List<User> findUsersByStatus(UserStatus status) throws DaoException;

    boolean updateUserStatus(long id, UserStatus status) throws DaoException;
}
