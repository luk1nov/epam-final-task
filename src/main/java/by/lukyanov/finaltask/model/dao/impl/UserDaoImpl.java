package by.lukyanov.finaltask.model.dao.impl;

import by.lukyanov.finaltask.model.dao.UserDao;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.model.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SQL_ADD_USER = "INSERT INTO users (email,password,name,surname,user_status,user_role,phone) values(?,?,?,?,?,?,?)";
    private static final String SQL_FIND_USER_BY_EMAIL = "SELECT users.user_id, users.email, users.name, users.surname, users.user_status, users.user_role, users.phone, users.balance FROM users WHERE email = ?";
    private static final String SQL_AUTHENTICATE_USER_BY_EMAIL_AND_PASS = "SELECT users.user_id, users.email, users.name, users.surname, users.user_status, users.user_role, users.phone FROM users WHERE email = ? AND password = ?";
    private static final String SQL_FIND_ALL_USERS = "SELECT users.user_id, users.email, users.name, users.surname , users.user_status , users.user_role, users.phone, users.balance FROM users";
    private static final String SQL_FIND_USER_BY_ID = "SELECT users.user_id, users.email, users.name, users.surname, users.user_status, users.user_role, users.phone, users.balance FROM users WHERE user_id = ?";
    private static final String SQL_EDIT_USER_BY_ID = "UPDATE users SET email = ?, name = ?, surname = ?, user_status = ?, user_role = ?, phone = ? WHERE user_id = ?";
    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE user_id = ?";
    private static final String SQL_FIND_USER_BALANCE_BY_ID = "SELECT users.balance FROM users WHERE user_id = ?";
    private static final String SQL_UPDATE_USER_BALANCE_BY_ID = "UPDATE users SET balance = ? WHERE user_id = ?";
    private static final ConnectionPool pool = ConnectionPool.getInstance();
    private static UserDaoImpl instance;


    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance(){
        if (instance == null){
            instance = new UserDaoImpl();
        }
        return instance;
    }


    public boolean insert(User user) throws DaoException {
        boolean inserted = false;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_USER)){
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setString(5, String.valueOf(user.getStatus()));
            statement.setString(6, String.valueOf(user.getRole()));
            statement.setString(7, user.getPhone());
            int result = statement.executeUpdate();
            if (result != 0){
                inserted = true;
                logger.info("user added - " + user);
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying insert the user", e);
            throw new DaoException(e);
        }
        return inserted;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        boolean result = false;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_BY_ID)){
            statement.setLong(1, id);
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
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_USERS);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()){
                User user = new User.UserBuilder()
                        .id(Long.parseLong(resultSet.getString(1)))
                        .email(resultSet.getString(2))
                        .name(resultSet.getString(3))
                        .surname(resultSet.getString(4))
                        .status(User.Status.valueOf(resultSet.getString(5)))
                        .role(User.Role.valueOf(resultSet.getString(6)))
                        .phone(resultSet.getString(7))
                        .balance(new BigDecimal(resultSet.getString(8)))
                        .build();
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying find all users", e);
            throw new DaoException(e);
        }
        return users;
    }

    @Override
    public boolean update(User user) throws DaoException {
        boolean result;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_EDIT_USER_BY_ID)){
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());
            statement.setString(4, String.valueOf(user.getStatus()));
            statement.setString(5, String.valueOf(user.getRole()));
            statement.setString(6, user.getPhone());
            statement.setLong(7, user.getId());
            int updatedLines = statement.executeUpdate();
            if (updatedLines != 0){
                result = true;
                logger.info("updated user " + user);
            } else {
                result = false;
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying update user", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public Optional<User> authenticate(String email, String password) throws DaoException {
        Optional<User> foundUser;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_AUTHENTICATE_USER_BY_EMAIL_AND_PASS)){
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    User user = new User.UserBuilder()
                            .id(Long.parseLong(resultSet.getString(1)))
                            .email(resultSet.getString(2))
                            .name(resultSet.getString(3))
                            .surname(resultSet.getString(4))
                            .status(User.Status.valueOf(resultSet.getString(5)))
                            .role(User.Role.valueOf(resultSet.getString(6)))
                            .phone(resultSet.getString(7))
                            .build();
                    logger.info("found user in db " + user.toString());
                    foundUser = Optional.of(user);
                } else {
                    foundUser = Optional.empty();
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying authenticate user by email & pass", e);
            throw new DaoException(e);
        }
        return foundUser;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        Optional<User> foundUser;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_EMAIL)){
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    User user = new User.UserBuilder()
                            .id(Long.parseLong(resultSet.getString(1)))
                            .email(resultSet.getString(2))
                            .name(resultSet.getString(3))
                            .surname(resultSet.getString(4))
                            .status(User.Status.valueOf(resultSet.getString(56)))
                            .role(User.Role.valueOf(resultSet.getString(6)))
                            .phone(resultSet.getString(7))
                            .balance(new BigDecimal(resultSet.getString(8)))
                            .build();
                    foundUser = Optional.of(user);
                } else {
                    foundUser = Optional.empty();
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying find the user by email", e);
            throw new DaoException(e);
        }
        return foundUser;
    }

    @Override
    public Optional<User> findUserById(String id) throws DaoException {
        Optional<User> foundUser;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_ID)){
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    User user = new User.UserBuilder()
                            .id(Long.parseLong(resultSet.getString(1)))
                            .email(resultSet.getString(2))
                            .name(resultSet.getString(3))
                            .surname(resultSet.getString(4))
                            .status(User.Status.valueOf(resultSet.getString(5)))
                            .role(User.Role.valueOf(resultSet.getString(6)))
                            .phone(resultSet.getString(7))
                            .balance(new BigDecimal(resultSet.getString(8)))
                            .build();
                    foundUser = Optional.of(user);
                } else {
                    foundUser = Optional.empty();
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying find the user by id", e);
            throw new DaoException(e);
        }
        return foundUser;
    }

    @Override
    public boolean refillBalance(long id, BigDecimal amount) throws DaoException {
        boolean updated = false;
        try (Connection connection = pool.getConnection()){
            try (PreparedStatement findUserBalanceStmt = connection.prepareStatement(SQL_FIND_USER_BALANCE_BY_ID);
                 PreparedStatement updateBalanceStmt = connection.prepareStatement(SQL_UPDATE_USER_BALANCE_BY_ID)){
                connection.setAutoCommit(false);
                findUserBalanceStmt.setLong(1, id);
                try (ResultSet rs = findUserBalanceStmt.executeQuery()){
                    if (rs.next()){
                        BigDecimal userBalance = rs.getBigDecimal(1);
                        userBalance = userBalance.add(amount);
                        updateBalanceStmt.setBigDecimal(1, userBalance);
                        updateBalanceStmt.setLong(2, id);
                        if (updateBalanceStmt.executeUpdate() != 0){
                            updated = true;
                            connection.commit();
                        }
                    } else {
                        logger.warn("User balance not found");
                        connection.rollback();
                    }
                }
            } catch (SQLException e){
                logger.error("Dao exception trying update balance - rollback", e);
                connection.rollback();
                throw new SQLException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying update balance", e);
            throw new DaoException(e);
        }
        return updated;
    }
}
