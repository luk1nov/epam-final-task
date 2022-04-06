package by.lukyanov.final_task.model.service.impl;

import by.lukyanov.final_task.command.ParameterAndAttribute;
import by.lukyanov.final_task.model.dao.impl.UserDaoImpl;
import by.lukyanov.final_task.entity.User;
import by.lukyanov.final_task.exception.DaoException;
import by.lukyanov.final_task.exception.ServiceException;
import by.lukyanov.final_task.model.service.UserService;
import by.lukyanov.final_task.util.PasswordEncoder;
import by.lukyanov.final_task.validation.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();

    @Override
    public Optional<User> authenticate(String email, String password) throws ServiceException {
        Optional<User> user;
        String encodedPassword;
        if(UserValidator.isValidEmail(email) && UserValidator.isValidPassword(password)){
            try {
                encodedPassword = PasswordEncoder.getInstance().encode(password);
                user = userDao.authenticate(email, encodedPassword);
            } catch (ServiceException | DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            user = Optional.empty();
        }
        return user;
    }

    @Override
    public boolean addUser(Map<String, String> userData) throws ServiceException {
        PasswordEncoder encoder = PasswordEncoder.getInstance();
        String name = userData.get(ParameterAndAttribute.USER_NAME);
        String surname = userData.get(ParameterAndAttribute.USER_SURNAME);
        String email = userData.get(ParameterAndAttribute.USER_EMAIL);
        String pass = userData.get(ParameterAndAttribute.USER_PASS);

        boolean addUserResult;

        if(UserValidator.isOneWord(name) && UserValidator.isValidSurname(surname) && UserValidator.isValidEmail(email) && UserValidator.isValidPassword(pass)){
            String encodedPass = encoder.encode(pass);
            User user = new User.UserBuilder()
                    .name(name)
                    .surname(surname)
                    .email(email)
                    .password(encodedPass)
                    .role(User.Role.USER)
                    .status(User.Status.INACTIVE)
                    .build();
            try {
                addUserResult = userDao.insert(user);
            } catch (DaoException e) {
                logger.error("Service exception trying add user", e);
                throw new ServiceException("Service exception trying add user", e);
            }
        } else {
            logger.info("provided didn't pass validation");
            addUserResult = false;
        }
        return addUserResult;
    }

    @Override
    public boolean deleteUser(String userId) throws ServiceException {
        boolean result = false;
        try {
            if(UserValidator.isValidId(userId)){
                Optional<User> optionalUser = userDao.findUserById(userId);
                if(optionalUser.isPresent()){
                    User user = optionalUser.get();
                    result = userDao.delete(user);
                } else {
                    logger.info("User not exists in deleteUser method");
                }
            } else {
                logger.info("Provided invalid userId in deleteUser method");
            }
        } catch (DaoException e) {
            logger.error("Service exception trying delete user");
            throw new ServiceException();
        }
        return result;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws ServiceException {
        Optional<User> user;
        try {
            if(UserValidator.isValidEmail(email)){
                user = userDao.findUserByEmail(email);
            } else {
                user = Optional.empty();
            }
        } catch (DaoException e) {
            logger.error("Service exception trying find user by email", e);
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        List<User> users;
        try {
            users = userDao.findAll();
        } catch (DaoException e) {
            logger.error("Service exception trying find all users");
            throw new ServiceException(e);
        }
        return users;
    }

    @Override
    public Optional<User> findUserById(String id) throws ServiceException {
        Optional<User> user;
        if (UserValidator.isValidId(id)){
            try {
                user = userDao.findUserById(id);
            } catch (DaoException e) {
                logger.error("Dao exception trying find user by id");
                throw new ServiceException(e);
            }
        } else {
            user = Optional.empty();
        }
        return user;
    }

    @Override
    public boolean updateUser(Map<String, String> userData) throws ServiceException {
        boolean updated;
        String userId = userData.get(ParameterAndAttribute.USER_ID);
        String name = userData.get(ParameterAndAttribute.USER_NAME);
        String surname = userData.get(ParameterAndAttribute.USER_SURNAME);
        String email = userData.get(ParameterAndAttribute.USER_EMAIL);
        String role = userData.get(ParameterAndAttribute.USER_ROLE).toUpperCase();
        String status = userData.get(ParameterAndAttribute.USER_STATUS).toUpperCase();
        logger.info(userData.toString());
        if(UserValidator.isValidId(userId) && UserValidator.isOneWord(name) && UserValidator.isValidSurname(surname) && UserValidator.isValidEmail(email)){
            try{
                User user = new User.UserBuilder()
                        .id(Long.parseLong(userId))
                        .name(name)
                        .surname(surname)
                        .email(email)
                        .role(User.Role.valueOf(role))
                        .status(User.Status.valueOf(status))
                        .build();
                updated = userDao.update(user);
            } catch (IllegalArgumentException ex) {
                logger.error("Service exception in updateUser non-existent role or status");
                throw new ServiceException(ex);
            } catch (DaoException e) {
                logger.error("Service exception trying update user");
                throw new ServiceException(e);
            }
        } else {
            logger.info("Provided invalid user data");
            updated = false;
        }
        return updated;
    }
}
