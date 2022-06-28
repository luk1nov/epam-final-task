package by.lukyanov.finaltask.model.dao.impl;

import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.entity.UserStatus;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.model.connection.ConnectionPool;
import by.lukyanov.finaltask.model.dao.UserDao;
import by.lukyanov.finaltask.model.dao.mapper.impl.UserRowMapper;
import by.lukyanov.finaltask.util.ImageEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.lukyanov.finaltask.model.dao.ColumnName.*;


public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SQL_ADD_USER = "INSERT INTO users (email,password,name,surname,user_status,user_role,phone) values(?,?,?,?,?,?,?)";
    private static final String SQL_FIND_USER_BY_EMAIL = "SELECT users.user_id, users.email, users.password, users.name, users.surname, users.user_status, users.user_role, users.phone, users.balance FROM users WHERE email = ?";
    private static final String SQL_FIND_USER_BY_PHONE = "SELECT users.user_id, users.email, users.password, users.name, users.surname, users.user_status, users.user_role, users.phone, users.balance FROM users WHERE phone = ?";
    private static final String SQL_FIND_ALL_USERS = "SELECT users.user_id, users.email, users.name, users.surname , users.user_status , users.user_role, users.phone, users.balance FROM users order by user_id LIMIT ? OFFSET ?";
    private static final String SQL_FIND_USER_BY_ID = "SELECT users.user_id, users.email, users.name, users.surname, users.user_status, users.user_role, users.phone, users.balance, users.driver_license_photo FROM users WHERE user_id = ?";
    private static final String SQL_EDIT_USER_BY_ID = "UPDATE users SET email = ?, name = ?, surname = ?, user_status = ?, user_role = ?, phone = ? WHERE user_id = ?";
    private static final String SQL_EDIT_USER_INFO_BY_ID = "UPDATE users SET name = ?, surname = ?, email = ?, phone = ? WHERE user_id = ?";
    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE user_id = ?";
    private static final String SQL_FIND_USER_BALANCE_BY_ID = "SELECT users.balance FROM users WHERE user_id = ?";
    private static final String SQL_UPDATE_USER_BALANCE_BY_ID = "UPDATE users SET balance = ? WHERE user_id = ?";
    private static final String SQL_UPDATE_DRIVER_LICENSE_AND_STATUS_BY_ID = "UPDATE users SET driver_license_photo = ?, user_status = ? WHERE user_id = ?";
    private static final String SQL_UPDATE_USER_STATUS_BY_ID = "UPDATE users SET user_status = ? WHERE user_id = ?";
    private static final String SQL_UPDATE_USER_PASSWORD_BY_ID = "UPDATE users SET password = ? WHERE user_id = ?";
    private static final String SQL_FIND_USERS_BY_STATUS = "SELECT user_id, email, name, surname, driver_license_photo FROM users WHERE user_status = ? order by user_id limit ? offset ?";
    private static final String SQL_COUNT_USERS = "SELECT COUNT(user_id) FROM users";
    private static final String SQL_COUNT_USERS_BY_STATUS = "SELECT COUNT(user_id) FROM users WHERE user_status = ?";
    private static final String SQL_SEARCH_USERS = """
            SELECT u.user_id, u.email, u.name, u.surname , u.user_status , u.user_role, u.phone, u.balance
            FROM users as u
            WHERE INSTR(u.email, ?) > 0
                    OR INSTR(u.name, ?) > 0
                    OR INSTR(u.surname, ?) > 0
            """;
    private static final ConnectionPool pool = ConnectionPool.getInstance();
    private static final ImageEncoder imageEncoder = ImageEncoder.getInstance();
    private static final UserRowMapper mapper = UserRowMapper.getInstance();
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
            statement.setString(5, user.getStatus().name());
            statement.setString(6, user.getRole().name());
            statement.setString(7, user.getPhone());
            if (statement.executeUpdate() != 0){
                inserted = true;
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
            if(statement.executeUpdate() != 0){
                result = true;
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying delete user");
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<User> findAll(int limit, int offset) throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_USERS)){
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<User> optionalUser = mapper.mapRow(resultSet);
                    optionalUser.ifPresent(users::add);
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying find all users", e);
            throw new DaoException(e);
        }
        return users;
    }

    @Override
    public boolean update(User user) throws DaoException {
        boolean result = false;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_EDIT_USER_BY_ID)){
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());
            statement.setString(4, user.getStatus().name());
            statement.setString(5, user.getRole().name());
            statement.setString(6, user.getPhone());
            statement.setLong(7, user.getId());
            if (statement.executeUpdate() != 0){
                result = true;
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying update user", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        Optional<User> foundUser;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_EMAIL)){
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    foundUser = mapper.mapRow(resultSet);
                    if (foundUser.isPresent()){
                        User user = foundUser.get();
                        user.setPassword(resultSet.getString(USER_PASS));
                        foundUser = Optional.of(user);
                    }
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
    public Optional<User> findUserById(long id) throws DaoException {
        Optional<User> foundUser;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_ID)){
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    foundUser = mapper.mapRow(resultSet);
                    if (foundUser.isPresent()){
                        User user = foundUser.get();
                        user.setDriverLicense(imageEncoder.decodeImage(resultSet.getBytes(USER_DRIVER_LICENSE)));
                        foundUser = Optional.of(user);
                    }
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
    public Optional<User> findUserByPhone(String phone) throws DaoException {
        Optional<User> foundUser;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_PHONE)){
            statement.setString(1, phone);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    foundUser = mapper.mapRow(resultSet);
                    if (foundUser.isPresent()){
                        User user = foundUser.get();
                        user.setPassword(resultSet.getString(USER_PASS));
                        foundUser = Optional.of(user);
                    }
                } else {
                    foundUser = Optional.empty();
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying find the user by phone", e);
            throw new DaoException(e);
        }
        return foundUser;
    }

    @Override
    public boolean updateBalance(long id, BigDecimal amount, boolean subtract) throws DaoException {
        boolean updated = false;
        try (Connection connection = pool.getConnection()){
            try (PreparedStatement findUserBalanceStmt = connection.prepareStatement(SQL_FIND_USER_BALANCE_BY_ID);
                 PreparedStatement updateBalanceStmt = connection.prepareStatement(SQL_UPDATE_USER_BALANCE_BY_ID)){
                connection.setAutoCommit(false);
                findUserBalanceStmt.setLong(1, id);
                try (ResultSet rs = findUserBalanceStmt.executeQuery()){
                    if (rs.next()){
                        BigDecimal userBalance = rs.getBigDecimal(1);
                        userBalance = subtract ? userBalance.subtract(amount) : userBalance.add(amount);
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

    @Override
    public boolean updateUserInfo(User user) throws DaoException {
        boolean result = false;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_EDIT_USER_INFO_BY_ID)){
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhone());
            statement.setLong(5, user.getId());
            if (statement.executeUpdate() != 0){
                result = true;
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying update user info", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public boolean updateDriverLicense(long id, InputStream image) throws DaoException {
        boolean result = false;
        try (Connection connection = pool.getConnection();
             PreparedStatement updateLicenseStmt = connection.prepareStatement(SQL_UPDATE_DRIVER_LICENSE_AND_STATUS_BY_ID)) {
            updateLicenseStmt.setBlob(1, image);
            updateLicenseStmt.setString(2, UserStatus.VERIFICATION.name());
            updateLicenseStmt.setLong(3, id);
            if (updateLicenseStmt.executeUpdate() != 0){
                result = true;
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying update driver license and set verification status", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public boolean updatePassword(long id, String password) throws DaoException {
        boolean result = false;
        try (Connection connection = pool.getConnection();
             PreparedStatement updatePasswordStmt = connection.prepareStatement(SQL_UPDATE_USER_PASSWORD_BY_ID)) {
            updatePasswordStmt.setString(1, password);
            updatePasswordStmt.setLong(2, id);
            if (updatePasswordStmt.executeUpdate() != 0){
                result = true;
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying update driver license and set verification status", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<User> findUsersByStatus(UserStatus status, int limit, int offset) throws DaoException {
        List<User> userList = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USERS_BY_STATUS)) {
            statement.setString(1, String.valueOf(status));
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            try (ResultSet rs = statement.executeQuery()){
                while (rs.next()){
                    User user = new User.UserBuilder()
                            .id(rs.getLong(USER_ID))
                            .email(rs.getString(USER_EMAIL))
                            .name(rs.getString(USER_NAME))
                            .surname(rs.getString(USER_SURNAME))
                            .driverLicense(imageEncoder.decodeImage(rs.getBytes(USER_DRIVER_LICENSE)))
                            .build();
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying find unverified users", e);
            throw new DaoException(e);
        }
        return userList;
    }

    @Override
    public boolean updateUserStatus(long id, UserStatus status) throws DaoException {
        boolean result = false;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_STATUS_BY_ID)){
            statement.setString(1, status.name());
            statement.setLong(2, id);
            if(statement.executeUpdate() != 0){
                result = true;
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying update user status", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public int countAllUsers() throws DaoException {
        int usersCount = 0;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_COUNT_USERS);
             ResultSet rs = statement.executeQuery()){
            if(rs.next()){
                usersCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying count all users", e);
            throw new DaoException(e);
        }
        return usersCount;
    }

    @Override
    public int countAllUsersByStatus(UserStatus status) throws DaoException {
        int usersCount = 0;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_COUNT_USERS_BY_STATUS)){
            statement.setString(1, status.name());
            try (ResultSet rs = statement.executeQuery()){
                if (rs.next()) {
                    usersCount = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying count users by status", e);
            throw new DaoException(e);
        }
        return usersCount;
    }

    @Override
    public List<User> searchUsers(String searchQuery) throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SEARCH_USERS)) {
            statement.setString(1, searchQuery);
            statement.setString(2, searchQuery);
            statement.setString(3, searchQuery);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<User> optionalUser = mapper.mapRow(resultSet);
                    optionalUser.ifPresent(users::add);
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying search users", e);
            throw new DaoException(e);
        }
        return users;
    }
}
