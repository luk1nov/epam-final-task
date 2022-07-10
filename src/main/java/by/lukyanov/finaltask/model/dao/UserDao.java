package by.lukyanov.finaltask.model.dao;

import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.entity.UserStatus;
import by.lukyanov.finaltask.exception.DaoException;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * The user data access object interface
 */
public interface UserDao extends BaseDao<User>{
    /**
     * Creates new user.
     *
     * @param user User object
     * @return true if user registered, otherwise false
     * @throws DaoException if there is a problem with data access
     */
    boolean insert(User user) throws DaoException;

    /**
     * Finds user with specified email.
     *
     * @param email user email
     * @return optional object of user
     * @throws DaoException if there is a problem with data access
     */
    Optional<User> findUserByEmail(String email) throws DaoException;

    /**
     * Finds user with specified user id.
     *
     * @param id user id
     * @return optional object of user
     * @throws DaoException if there is a problem with data access
     */
    Optional<User> findUserById(long id) throws DaoException;

    /**
     * Finds user with specified phone.
     *
     * @param phone user phone
     * @return optional object of user
     * @throws DaoException if there is a problem with data access
     */
    Optional<User> findUserByPhone(String phone) throws DaoException;

    /**
     * Updates user balance with specified user id, amount and type of update(refill or withdraw).
     *
     * @param id user id
     * @param amount of money to refill or withdraw
     * @param subtract true for withdraw, false for refill
     * @return true if balance refilled successfully, otherwise false
     * @throws DaoException if there is a problem with data access
     */
    boolean updateBalance(long id, BigDecimal amount, boolean subtract) throws DaoException;

    /**
     * Updates user info.
     *
     * @param user object of user
     * @return true if user info updated, otherwise false
     * @throws DaoException if there is a problem with data access
     */
    boolean updateUserInfo(User user) throws DaoException;

    /**
     * Upload driver license photo with specified user id.
     *
     * @param id user id
     * @param image InputStream of driver license image
     * @return true if driver license photo updated, otherwise false
     * @throws DaoException if there is a problem with data access
     */
    boolean updateDriverLicense(long id, InputStream image) throws DaoException;

    /**
     * Updates user password with specified user id and plain password.
     *
     * @param id user id
     * @param password encrypted user new password
     * @return true if password updated, otherwise false
     * @throws DaoException if there is a problem with data access
     */
    boolean updatePassword(long id, String password) throws DaoException;

    /**
     * Finds users with specified user status for requested page.
     *
     * @param status user status
     * @param limit requested row limit
     * @param offset from which row to get the result
     * @return List of users with requested status
     * @throws DaoException if there is a problem with data access
     */
    List<User> findUsersByStatus(UserStatus status, int limit, int offset) throws DaoException;

    /**
     * Updates user status with specified user id.
     *
     * @param id user id
     * @param status user status
     * @return true if user status updated, otherwise false
     * @throws DaoException if there is a problem with data access
     */
    boolean updateUserStatus(long id, UserStatus status) throws DaoException;

    /**
     * Counts all users.
     *
     * @return number of all users
     * @throws DaoException if there is a problem with data access
     */
    int countAllUsers() throws DaoException;

    /**
     * Counts all users with specified user status.
     *
     * @param status user status
     * @return number of users with requested status
     * @throws DaoException if there is a problem with data access
     */
    int countAllUsersByStatus(UserStatus status) throws DaoException;

    /**
     * Searches users by name, surname and email.
     *
     * @param searchQuery search query
     * @return List of users where email, name or surname contains search query
     * @throws DaoException if there is a problem with data access
     */
    List<User> searchUsers(String searchQuery) throws DaoException;
}
