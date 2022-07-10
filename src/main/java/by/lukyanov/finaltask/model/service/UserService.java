package by.lukyanov.finaltask.model.service;

import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.entity.UserStatus;
import by.lukyanov.finaltask.exception.ServiceException;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The user service interface.
 */
public interface UserService {
    /**
     * Register new user with specified user data.
     *
     * @param userData Map of user data
     * @return true if data is valid and user added, otherwise false
     * @throws ServiceException if there is a problem with user service
     */
    boolean addUser(Map<String, String> userData) throws ServiceException;

    /**
     * Deletes user with specified user id, if user not try to delete itself.
     *
     * @param userId user id
     * @param loggedUserId logged user id
     * @return true if user deleted, otherwise false
     * @throws ServiceException if there is a problem with user service
     */
    boolean deleteUser(String userId, long loggedUserId) throws ServiceException;

    /**
     * Finds user with specified email.
     *
     * @param email user email
     * @return optional object of User
     * @throws ServiceException if there is a problem with user service
     */
    Optional<User> findUserByEmail(String email) throws ServiceException;

    /**
     * Finds user with specified phone.
     *
     * @param phone user phone
     * @return optional object of User
     * @throws ServiceException if there is a problem with user service
     */
    Optional<User> findUserByPhone(String phone) throws ServiceException;

    /**
     * Finds all users for requested page.
     *
     * @param pageNumber requested number of page
     * @param postsPerPage number of posts per page
     * @return List of users for requested page
     * @throws ServiceException if there is a problem with user service
     */
    List<User> findAllUsers(String pageNumber, int postsPerPage) throws ServiceException;

    /**
     * Finds user with specified user id.
     *
     * @param id user id
     * @return optional object of User
     * @throws ServiceException if there is a problem with user service
     */
    Optional<User> findUserById(String id) throws ServiceException;

    /**
     * Updates user with specified user data.
     *
     * @param userData Map of user data
     * @return true if user data is valid and user updated, otherwise false
     * @throws ServiceException if there is a problem with user service
     */
    boolean updateUser(Map<String, String> userData) throws ServiceException;

    /**
     * Refills user's balance with specified user id.
     *
     * @param id user id
     * @param amount amount of money for refill
     * @return true if balance refilled, otherwise false
     * @throws ServiceException if there is a problem with user service
     */
    boolean refillBalance(long id, String amount) throws ServiceException;

    /**
     * Updates user's info with specified user id.
     *
     * @param id logged user id
     * @param userData new user data
     * @return true if data is valid and user info updated, otherwise false
     * @throws ServiceException if there is a problem with user service
     */
    boolean updateUserInfo(long id, Map<String, String> userData) throws ServiceException;

    /**
     * Uploads driver license photo wit specified user id.
     *
     * @param id logged user id
     * @param image InputStream of driver license photo
     * @return true if InputStream not empty and photo uploaded, otherwise false
     * @throws ServiceException if there is a problem with user service
     */
    boolean updateDriverLicense(long id, InputStream image) throws ServiceException;

    /**
     * Changes user password with specified user id.
     *
     * @param id user id
     * @param password new plain password
     * @return true if password changed, otherwise false
     * @throws ServiceException if there is a problem with user service
     */
    boolean changeUserPassword(long id, String password) throws ServiceException;

    /**
     * Finds users with specified user status for requested page.
     *
     * @param status user status
     * @param pageNumber number of requested page
     * @param postsPerPage number of posts per page
     * @return List of users with requested user status for requested page
     * @throws ServiceException if there is a problem with user service
     */
    List<User> findUsersByStatus(UserStatus status, String pageNumber, int postsPerPage) throws ServiceException;

    /**
     * Updates user status with specified user id.
     *
     * @param id user id
     * @param status user status
     * @return true if user status updated, otherwise false
     * @throws ServiceException if there is a problem with user service
     */
    boolean updateUserStatus(String id, UserStatus status) throws ServiceException;

    /**
     * Counts all users.
     *
     * @return number of all users
     * @throws ServiceException if there is a problem with user service.
     */
    int countAllUsers() throws ServiceException;

    /**
     * Counts all users with specified user status.
     *
     * @param status user status
     * @return number of users with requested user status
     * @throws ServiceException if there is a problem with user service
     */
    int countAllUsersByStatus(UserStatus status) throws ServiceException;

    /**
     * Searches users by name, surname and email.
     *
     * @param searchQuery search query
     * @return List of searched users
     * @throws ServiceException if there is a problem with user service
     */
    List<User> searchUsers(String searchQuery) throws ServiceException;
}
