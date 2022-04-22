package by.lukyanov.finaltask.model.service.impl;

import by.lukyanov.finaltask.command.ParameterAndAttribute;
import by.lukyanov.finaltask.model.dao.impl.UserDaoImpl;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.UserService;
import by.lukyanov.finaltask.util.PasswordEncoder;
import by.lukyanov.finaltask.validation.impl.ValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();
    private static final ValidatorImpl validator = ValidatorImpl.getInstance();
    private static final String PHONE_CODE_BY = "+375-";

    @Override
    public Optional<User> authenticate(String email, String password) throws ServiceException {
        Optional<User> user;
        String encodedPassword;
        if(validator.isValidEmail(email) && validator.isValidPassword(password)){
            try {
                encodedPassword = PasswordEncoder.getInstance().encode(password);
                user = userDao.authenticate(email, encodedPassword);
            } catch (DaoException e) {
                logger.error("Service exception trying authenticate user by email & password", e);
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
        String phone = userData.get(ParameterAndAttribute.USER_PHONE);
        String pass = userData.get(ParameterAndAttribute.USER_PASS);
        boolean addUserResult;

        if(validator.isOneWord(name) && validator.isValidSurname(surname) && validator.isValidEmail(email) &&
                validator.isValidPassword(pass) && validator.isValidPhone(phone)){
            String encodedPass = encoder.encode(pass);
            String phoneWithCountryCode = PHONE_CODE_BY + phone;
            User user = new User.UserBuilder()
                    .name(name)
                    .surname(surname)
                    .email(email)
                    .phone(phoneWithCountryCode)
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
            logger.info("provided data didn't pass validation");
            addUserResult = false;
        }
        return addUserResult;
    }

    @Override
    public boolean deleteUser(String userId) throws ServiceException {
        boolean result = false;
        try {
            if(validator.isValidId(userId)){
                result = userDao.delete(Long.parseLong(userId));
            } else {
                logger.info("Provided invalid userId in deleteUser method");
            }
        } catch (DaoException e) {
            logger.error("Service exception trying delete user", e);
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws ServiceException {
        Optional<User> user;
        try {
            if(validator.isValidEmail(email)){
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
            logger.error("Service exception trying find all users", e);
            throw new ServiceException(e);
        }
        return users;
    }

    @Override
    public Optional<User> findUserById(String id) throws ServiceException {
        Optional<User> user;
        if (validator.isValidId(id)){
            try {
                user = userDao.findUserById(id);
            } catch (DaoException e) {
                logger.error("Service exception trying find user by id", e);
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
        String phone = userData.get(ParameterAndAttribute.USER_PHONE);
        String role = userData.get(ParameterAndAttribute.USER_ROLE).toUpperCase();
        String status = userData.get(ParameterAndAttribute.USER_STATUS).toUpperCase();
        logger.info(userData.toString());
        if(validator.isValidId(userId) && validator.isOneWord(name) && validator.isValidSurname(surname) &&
                validator.isValidEmail(email) && validator.isValidPhone(phone)){
            try{
                String phoneWithCountryCode = PHONE_CODE_BY + phone;
                User user = new User.UserBuilder()
                        .id(Long.parseLong(userId))
                        .name(name)
                        .surname(surname)
                        .email(email)
                        .phone(phoneWithCountryCode)
                        .role(User.Role.valueOf(role))
                        .status(User.Status.valueOf(status))
                        .build();
                updated = userDao.update(user);
            } catch (IllegalArgumentException ex) {
                logger.error("Service exception in updateUser non-existent role or status", ex);
                throw new ServiceException(ex);
            } catch (DaoException e) {
                logger.error("Service exception trying update user", e);
                throw new ServiceException(e);
            }
        } else {
            logger.info("Provided invalid user data");
            updated = false;
        }
        return updated;
    }

    @Override
    public boolean refillBalance(long id, String amount) throws ServiceException {
        boolean updated = false;
        if (validator.isValidPrice(amount)){
            try {
                updated = userDao.refillBalance(id, new BigDecimal(amount));
            } catch (DaoException e) {
               logger.error("Service exception trying refill balance", e);
               throw new ServiceException(e);
            }
        }
        return updated;
    }
}
