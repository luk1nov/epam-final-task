package by.lukanov.final_task.dao.impl;

import by.lukanov.final_task.dao.UserDao;
import by.lukanov.final_task.entity.User;
import by.lukanov.final_task.exception.DaoException;
import by.lukanov.final_task.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SQL_ADD_USER = "INSERT INTO users (email,password,name,surname,user_status,user_role) values(?,?,?,?,?,?)";
    private static final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String SQL_AUTHENTICATE_USER_BY_EMAIL_AND_PASS = "SELECT * FROM users WHERE email = ? AND password = ?";
    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM users";
    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE user_id = ?";
    private static final String SQL_EDIT_USER_BY_ID = "UPDATE users SET email = ?, name = ?, surname = ?, user_status = ?, user_role = ? WHERE user_id = ?";
    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE user_id = ?";
    private static UserDaoImpl instance;

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance(){
        if (instance == null){
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean insert(User user) throws DaoException {
        boolean inserted = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_USER)){
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setString(5, String.valueOf(user.getStatus()));
            statement.setString(6, String.valueOf(user.getRole()));
            int result = statement.executeUpdate();
            if (result != 0){
                inserted = true;
                logger.info("user added - " + user);
            }
        } catch (SQLException e) {
            logger.error("SQL exception trying insert the user", e);
            throw new DaoException(e);
        }
        return inserted;
    }

    @Override
    public boolean delete(User user) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        boolean result = false;
        try (Connection connection = pool.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_BY_ID)){
            statement.setString(1, String.valueOf(user.getId()));
            int resultLines = statement.executeUpdate();
            if(resultLines != 0){
                result = true;
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying delete user");
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<User> findAllUsers() throws DaoException {
        List<User> users = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_USERS);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()){
                User user = new User.UserBuilder()
                        .id(Long.parseLong(resultSet.getString(1)))
                        .email(resultSet.getString(2))
                        .password(resultSet.getString(3))
                        .name(resultSet.getString(4))
                        .surname(resultSet.getString(5))
                        .status(User.Status.valueOf(resultSet.getString(6)))
                        .role(User.Role.valueOf(resultSet.getString(7)))
                        .balance(BigDecimal.valueOf(Double.parseDouble(resultSet.getString(8))))
                        .build();
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("SQL exception trying authenticate user by email & pass", e);
            throw new DaoException(e);
        }
        return users;
    }

    @Override
    public boolean update(User user) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        boolean result;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_EDIT_USER_BY_ID)){
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());
            statement.setString(4, String.valueOf(user.getStatus()));
            statement.setString(5, String.valueOf(user.getRole()));
            statement.setString(6, String.valueOf(user.getId()));
            int updatedLines = statement.executeUpdate();
            if (updatedLines != 0){
                result = true;
                logger.info("updated user " + user);
            } else {
                result = false;
            }
        } catch (SQLException e) {
            logger.error("SQL exception trying authenticate user by email & pass", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public Optional<User> authenticate(String email, String password) throws DaoException {
        Optional<User> foundUser;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_AUTHENTICATE_USER_BY_EMAIL_AND_PASS)){
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                User user = new User.UserBuilder()
                        .id(Long.parseLong(resultSet.getString(1)))
                        .email(resultSet.getString(2))
                        .password(resultSet.getString(3))
                        .name(resultSet.getString(4))
                        .surname(resultSet.getString(5))
                        .status(User.Status.valueOf(resultSet.getString(6)))
                        .role(User.Role.valueOf(resultSet.getString(7)))
                        .build();
                logger.info("found user in db " + user.toString());
                foundUser = Optional.of(user);
            } else {
                foundUser = Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("SQL exception trying authenticate user by email & pass", e);
            throw new DaoException(e);
        }
        return foundUser;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        Optional<User> foundUser;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_EMAIL)){
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                User user = new User.UserBuilder()
                        .id(Long.parseLong(resultSet.getString(1)))
                        .email(resultSet.getString(2))
                        .password(resultSet.getString(3))
                        .name(resultSet.getString(4))
                        .surname(resultSet.getString(5))
                        .status(User.Status.valueOf(resultSet.getString(6)))
                        .role(User.Role.valueOf(resultSet.getString(7)))
                        .build();
                foundUser = Optional.of(user);
            } else {
                foundUser = Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("SQL exception trying find the user by email", e);
            throw new DaoException(e);
        }
        return foundUser;
    }

    @Override
    public Optional<User> findUserById(String id) throws DaoException {
        Optional<User> foundUser;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_ID)){
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                User user = new User.UserBuilder()
                        .id(Long.parseLong(resultSet.getString(1)))
                        .email(resultSet.getString(2))
                        .password(resultSet.getString(3))
                        .name(resultSet.getString(4))
                        .surname(resultSet.getString(5))
                        .status(User.Status.valueOf(resultSet.getString(6)))
                        .role(User.Role.valueOf(resultSet.getString(7)))
                        .build();
                foundUser = Optional.of(user);
            } else {
                foundUser = Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("SQL exception trying find the user by id", e);
            throw new DaoException(e);
        }
        return foundUser;
    }
}
