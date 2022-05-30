package by.lukyanov.finaltask.model.service.impl;

import by.lukyanov.finaltask.command.ParameterAttributeName;
import by.lukyanov.finaltask.entity.UserRole;
import by.lukyanov.finaltask.entity.UserStatus;
import by.lukyanov.finaltask.model.dao.impl.UserDaoImpl;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.UserService;
import by.lukyanov.finaltask.util.PasswordEncoder;
import by.lukyanov.finaltask.util.ResultCounter;
import by.lukyanov.finaltask.validation.impl.ValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.lukyanov.finaltask.util.ResultCounter.ROWS_PER_PAGE;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();
    private static final ValidatorImpl validator = ValidatorImpl.getInstance();
    private static final String PHONE_CODE_BY = "+375-";

    @Override
    public Optional<User> authenticate(String email, String password) throws ServiceException {
        Optional<User> user;
        if(validator.isValidEmail(email) && validator.isValidPassword(password)){
            try {
                user = userDao.authenticate(email, password);
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
        String name = userData.get(ParameterAttributeName.USER_NAME);
        String surname = userData.get(ParameterAttributeName.USER_SURNAME);
        String email = userData.get(ParameterAttributeName.USER_EMAIL);
        String phone = userData.get(ParameterAttributeName.USER_PHONE);
        String pass = userData.get(ParameterAttributeName.USER_PASS);
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
                    .role(UserRole.USER)
                    .status(UserStatus.INACTIVE)
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
    public List<User> findAllUsers(String pageNumber) throws ServiceException {
        List<User> users;
        try {
            int requestedPage = validator.isValidNumber(pageNumber) ? Integer.parseInt(pageNumber) : 1;
            ResultCounter counter = new ResultCounter(requestedPage);
            users = userDao.findAll(ROWS_PER_PAGE, counter.offset());
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
                user = userDao.findUserById(Long.parseLong(id));
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
        String userId = userData.get(ParameterAttributeName.USER_ID);
        String name = userData.get(ParameterAttributeName.USER_NAME);
        String surname = userData.get(ParameterAttributeName.USER_SURNAME);
        String email = userData.get(ParameterAttributeName.USER_EMAIL);
        String phone = userData.get(ParameterAttributeName.USER_PHONE);
        String role = userData.get(ParameterAttributeName.USER_ROLE).toUpperCase();
        String status = userData.get(ParameterAttributeName.USER_STATUS).toUpperCase();
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
                        .role(UserRole.valueOf(role))
                        .status(UserStatus.valueOf(status))
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
                updated = userDao.updateBalance(id, new BigDecimal(amount), false);
            } catch (DaoException e) {
               logger.error("Service exception trying refill balance", e);
               throw new ServiceException(e);
            }
        }
        return updated;
    }

    @Override
    public boolean updateUserInfo(long id, Map<String, String> userData) throws ServiceException {
        boolean updated = false;
        String name = userData.get(ParameterAttributeName.USER_NAME);
        String surname = userData.get(ParameterAttributeName.USER_SURNAME);
        String email = userData.get(ParameterAttributeName.USER_EMAIL);
        String phone = userData.get(ParameterAttributeName.USER_PHONE);
        if(validator.isOneWord(name) && validator.isValidSurname(surname) &&
                validator.isValidEmail(email) && validator.isValidPhone(phone)){
            try{
                String phoneWithCountryCode = PHONE_CODE_BY + phone;
                User user = new User.UserBuilder()
                        .id(id)
                        .name(name)
                        .surname(surname)
                        .email(email)
                        .phone(phoneWithCountryCode)
                        .build();
                updated = userDao.updateUserInfo(user);
            } catch (DaoException e) {
                logger.error("Service exception trying update user info", e);
                throw new ServiceException(e);
            }
        } else {
            logger.info("Provided invalid user data");
        }
        return updated;
    }

    @Override
    public boolean updateDriverLicense(long id, InputStream image) throws ServiceException {
        boolean result = false;
        try {
            if (image.available() != 0){
                result = userDao.updateDriverLicense(id, image);
            }
        } catch (DaoException | IOException e) {
            logger.error("Service exception trying update driver license", e);
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean changeUserPassword(long id, String password) throws ServiceException {
        boolean result = false;
        if (validator.isValidPassword(password)){
            try {
                result = userDao.updatePassword(id, PasswordEncoder.getInstance().encode(password));
            } catch (DaoException e) {
                logger.error("Service exception trying update driver license", e);
                throw new ServiceException(e);
            }
        } else {
            logger.warn("provided bad password");
        }
        return result;
    }

    @Override
    public List<User> findUsersByStatus(UserStatus status, String pageNumber) throws ServiceException {
        List<User> users;
        try {
            int requestedPage = validator.isValidNumber(pageNumber) ? Integer.parseInt(pageNumber) : 1;
            ResultCounter counter = new ResultCounter(requestedPage);
            users = userDao.findUsersByStatus(status, ROWS_PER_PAGE, counter.offset());
        } catch (DaoException e) {
            logger.error("Service exception trying find users by status", e);
            throw new ServiceException(e);
        }
        return users;
    }

    @Override
    public boolean updateUserStatus(String id, UserStatus status) throws ServiceException {
        boolean result = false;
        if(validator.isValidId(id)){
            try{
                result = userDao.updateUserStatus(Long.parseLong(id), status);
            } catch (DaoException e) {
                logger.error("Service exception trying update user status", e);
                throw new ServiceException(e);
            }
        }
        return result;
    }

    @Override
    public int countAllUsers() throws ServiceException {
        try {
            return userDao.countAllUsers();
        } catch (DaoException e) {
            logger.error("Service exception trying count all users", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public int countAllUsersByStatus(UserStatus status) throws ServiceException {
        try {
            return userDao.countAllUsersByStatus(status);
        } catch (DaoException e) {
            logger.error("Service exception trying count all users", e);
            throw new ServiceException(e);
        }
    }
}
