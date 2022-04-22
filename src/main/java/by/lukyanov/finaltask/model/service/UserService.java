package by.lukyanov.finaltask.model.service;

import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<User> authenticate(String login, String password) throws ServiceException;

    boolean addUser(Map<String, String> userData) throws ServiceException;

    boolean deleteUser(String userId) throws ServiceException;

    Optional<User> findUserByEmail(String email) throws ServiceException;

    List<User> findAllUsers() throws ServiceException;

    Optional<User> findUserById(String id) throws ServiceException;

    boolean updateUser(Map<String, String> userData) throws ServiceException;

    boolean refillBalance(long id, String amount) throws ServiceException;
}
