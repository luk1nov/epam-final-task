package by.lukanov.final_task.service;

import by.lukanov.final_task.entity.User;
import by.lukanov.final_task.exception.ServiceException;

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
}
