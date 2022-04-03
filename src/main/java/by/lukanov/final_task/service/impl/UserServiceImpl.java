package by.lukanov.final_task.service.impl;

import by.lukanov.final_task.command.ParameterAndAttribute;
import by.lukanov.final_task.dao.UserDao;
import by.lukanov.final_task.dao.impl.UserDaoImpl;
import by.lukanov.final_task.entity.User;
import by.lukanov.final_task.exception.DaoException;
import by.lukanov.final_task.exception.ServiceException;
import by.lukanov.final_task.service.UserService;
import by.lukanov.final_task.util.PasswordEncoder;
import by.lukanov.final_task.validation.UserValidator;
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
        String name = userData.get(ParameterAndAttribute.USER_NAME.getAttr());
        String surname = userData.get(ParameterAndAttribute.USER_SURNAME.getAttr());
        String email = userData.get(ParameterAndAttribute.USER_EMAIL.getAttr());
        String pass = userData.get(ParameterAndAttribute.USER_PASS.getAttr());

        boolean addUserResult;

        if(UserValidator.isValidName(name) && UserValidator.isValidSurname(surname) && UserValidator.isValidEmail(email) && UserValidator.isValidPassword(pass)){
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
            users = userDao.findAllUsers();
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
}
