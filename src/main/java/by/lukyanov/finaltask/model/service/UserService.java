package by.lukyanov.finaltask.model.service;

import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.entity.UserStatus;
import by.lukyanov.finaltask.exception.ServiceException;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<User> authenticate(String login, String password) throws ServiceException;

    boolean addUser(Map<String, String> userData) throws ServiceException;

    boolean deleteUser(String userId) throws ServiceException;

    Optional<User> findUserByEmail(String email) throws ServiceException;

    List<User> findAllUsers(String pageNumber, int postsPerPage) throws ServiceException;

    Optional<User> findUserById(String id) throws ServiceException;

    boolean updateUser(Map<String, String> userData) throws ServiceException;

    boolean refillBalance(long id, String amount) throws ServiceException;

    boolean updateUserInfo(long id, Map<String, String> userData) throws ServiceException;

    boolean updateDriverLicense(long id, InputStream image) throws ServiceException;

    boolean changeUserPassword(long id, String password) throws ServiceException;

    List<User> findUsersByStatus(UserStatus status, String pageNumber, int postsPerPage) throws ServiceException;

    boolean updateUserStatus(String id, UserStatus status) throws ServiceException;

    int countAllUsers() throws ServiceException;

    int countAllUsersByStatus(UserStatus status) throws ServiceException;

    List<User> searchUsers(String searchQuery) throws ServiceException;
}
