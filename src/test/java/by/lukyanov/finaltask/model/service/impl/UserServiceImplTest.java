package by.lukyanov.finaltask.model.service.impl;

import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.entity.UserStatus;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.dao.impl.UserDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static factory.UserFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.openMocks;

class UserServiceImplTest {
    private static final int DIGIT_TEN_INT = 10;
    private static final String DIGIT_ONE_STR = "1";
    private static final long DIGIT_ONE_LONG = 1L;

    @Mock
    private UserDaoImpl userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void addUserShouldReturnTrue() throws DaoException, ServiceException {
        given(userDao.insert(any(User.class))).willReturn(true);
        assertTrue(userService.addUser(createValidUserData()));
    }

    @Test
    void addUserShouldReturnFalse() throws DaoException, ServiceException {
        given(userDao.insert(any(User.class))).willReturn(false);
        assertFalse(userService.addUser(createInvalidUserData()));
    }

    @Test
    void addUserShouldThrowException() throws DaoException {
        given(userDao.insert(any(User.class))).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> userService.addUser(createValidUserData()));
    }

    @Test
    void deleteUserShouldReturnTrue() throws DaoException, ServiceException {
        long loggedUserId = 2L;
        given(userDao.delete(anyLong())).willReturn(true);
        assertTrue(userService.deleteUser(DIGIT_ONE_STR, loggedUserId));
    }

    @Test
    void deleteUserShouldReturnFalse() throws DaoException, ServiceException {
        long loggedUserId = 2L;
        given(userDao.delete(anyLong())).willReturn(false);
        assertFalse(userService.deleteUser(DIGIT_ONE_STR, loggedUserId));
    }

    @Test
    void deleteUserShouldThrowException() throws DaoException {
        given(userDao.delete(anyLong())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> userService.deleteUser(DIGIT_ONE_STR, DIGIT_ONE_LONG));
    }

    @Test
    void findUserByEmailShouldExists() throws DaoException, ServiceException {
        User user = createUser();
        given(userDao.findUserByEmail(anyString())).willReturn(Optional.of(user));
        assertEquals(user, userService.findUserByEmail(user.getEmail()).get());
    }

    @Test
    void findUserByEmailShouldNotExists() throws DaoException, ServiceException {
        User user = createUser();
        given(userDao.findUserByEmail(anyString())).willReturn(Optional.empty());
        assertThat(userService.findUserByEmail(user.getEmail())).isEmpty();
    }

    @Test
    void findUserByEmailShouldThrowException() throws DaoException {
        User user = createUser();
        given(userDao.findUserByEmail(anyString())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> userService.findUserByEmail(user.getEmail()));
    }


    @Test
    void findUserByPhoneShouldExists() throws DaoException, ServiceException {
        User user = createUser();
        given(userDao.findUserByPhone(anyString())).willReturn(Optional.of(user));
        assertEquals(user, userService.findUserByPhone(user.getPhone()).get());
    }

    @Test
    void findUserByPhoneShouldNotExists() throws DaoException, ServiceException {
        User user = createUser();
        given(userDao.findUserByPhone(anyString())).willReturn(Optional.empty());
        assertThat(userService.findUserByPhone(user.getPhone())).isEmpty();
    }

    @Test
    void findUserByPhoneShouldThrowException() throws DaoException {
        User user = createUser();
        given(userDao.findUserByPhone(anyString())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> userService.findUserByPhone(user.getPhone()));
    }

    @Test
    void findAllUsersShouldReturnFilled() throws DaoException, ServiceException {
        List<User> users = List.of(createUser());
        given(userDao.findAll(anyInt(), anyInt())).willReturn(users);
        assertThat(userService.findAllUsers(DIGIT_ONE_STR, DIGIT_TEN_INT))
                .isNotEmpty()
                .isEqualTo(users);
    }

    @Test
    void findAllUsersShouldReturnEmpty() throws DaoException, ServiceException {
        given(userDao.findAll(anyInt(), anyInt())).willReturn(new ArrayList<>());
        assertThat(userService.findAllUsers(DIGIT_ONE_STR, DIGIT_TEN_INT)).isEmpty();
    }

    @Test
    void findAllUsersShouldThrowException() throws DaoException {
        given(userDao.findAll(anyInt(), anyInt())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> userService.findAllUsers(DIGIT_ONE_STR, DIGIT_TEN_INT));
    }

    @Test
    void findUserByIdShouldExists() throws DaoException, ServiceException {
        User user = createUser();
        String id = String.valueOf(user.getId());
        given(userDao.findUserById(anyLong())).willReturn(Optional.of(user));
        assertEquals(user, userService.findUserById(id).get());
    }

    @Test
    void findUserByIdShouldNotExists() throws DaoException, ServiceException {
        User user = createUser();
        String id = String.valueOf(user.getId());
        given(userDao.findUserById(anyLong())).willReturn(Optional.empty());
        assertThat(userService.findUserById(id)).isEmpty();
    }

    @Test
    void findUserByIdShouldThrowException() throws DaoException {
        given(userDao.findUserById(anyLong())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> userService.findUserById(DIGIT_ONE_STR));
    }

    @Test
    void updateUserShouldReturnTrue() throws DaoException, ServiceException {
        given(userDao.update(any(User.class))).willReturn(true);
        assertTrue(userService.updateUser(createValidUserData()));
    }

    @Test
    void updateUserShouldReturnFalse() throws DaoException, ServiceException {
        given(userDao.update(any(User.class))).willReturn(false);
        assertFalse(userService.updateUser(createValidUserData()));
    }

    @Test
    void updateUserShouldThrowException() throws DaoException {
        given(userDao.update(any(User.class))).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> userService.updateUser(createValidUserData()));
    }

    @Test
    void refillBalanceShouldReturnTrue() throws DaoException, ServiceException {
        given(userDao.updateBalance(anyLong(), any(BigDecimal.class), anyBoolean())).willReturn(true);
        assertTrue(userService.refillBalance(DIGIT_ONE_LONG, DIGIT_ONE_STR));
    }

    @Test
    void refillBalanceShouldReturnFalse() throws DaoException, ServiceException {
        given(userDao.updateBalance(anyLong(), any(BigDecimal.class), anyBoolean())).willReturn(false);
        assertFalse(userService.refillBalance(DIGIT_ONE_LONG, DIGIT_ONE_STR));
    }

    @Test
    void refillBalanceShouldThrowException() throws DaoException {
        given(userDao.updateBalance(anyLong(), any(BigDecimal.class), anyBoolean())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> userService.refillBalance(DIGIT_ONE_LONG, DIGIT_ONE_STR));
    }

    @Test
    void updateUserInfoShouldReturnTrue() throws DaoException, ServiceException {
        given(userDao.updateUserInfo(any(User.class))).willReturn(true);
        assertTrue(userService.updateUserInfo(DIGIT_ONE_LONG, createValidUserData()));
    }

    @Test
    void updateUserInfoShouldReturnFalse() throws DaoException, ServiceException {
        given(userDao.updateUserInfo(any(User.class))).willReturn(false);
        assertFalse(userService.updateUserInfo(DIGIT_ONE_LONG, createValidUserData()));
    }

    @Test
    void updateUserInfoShouldThrowException() throws DaoException {
        given(userDao.updateUserInfo(any(User.class))).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> userService.updateUserInfo(DIGIT_ONE_LONG, createValidUserData()));
    }

    @Test
    void updateDriverLicenseShouldReturnTrue() throws DaoException, ServiceException {
        InputStream inputStream = new ByteArrayInputStream(new byte[64]);
        given(userDao.updateDriverLicense(anyLong(), any(InputStream.class))).willReturn(true);
        assertTrue(userService.updateDriverLicense(DIGIT_ONE_LONG, inputStream));
    }

    @Test
    void updateDriverLicenseShouldReturnFalse() throws DaoException, ServiceException {
        InputStream inputStream = new ByteArrayInputStream(new byte[64]);
        given(userDao.updateDriverLicense(anyLong(), any(InputStream.class))).willReturn(false);
        assertFalse(userService.updateDriverLicense(DIGIT_ONE_LONG, inputStream));
    }

    @Test
    void updateDriverLicenseShouldThrowException() throws DaoException {
        InputStream inputStream = new ByteArrayInputStream(new byte[64]);
        given(userDao.updateDriverLicense(anyLong(), any(InputStream.class))).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> userService.updateDriverLicense(DIGIT_ONE_LONG, inputStream));
    }

    @Test
    void changeUserPasswordShouldReturnTrue() throws DaoException, ServiceException {
        String password = "12345678Abc";
        given(userDao.updatePassword(anyLong(), anyString())).willReturn(true);
        assertTrue(userService.changeUserPassword(DIGIT_ONE_LONG, password));
    }

    @Test
    void changeUserPasswordShouldReturnFalse() throws DaoException, ServiceException {
        String password = "12345678Abc";
        given(userDao.updatePassword(anyLong(), anyString())).willReturn(false);
        assertFalse(userService.changeUserPassword(DIGIT_ONE_LONG, password));
    }

    @Test
    void changeUserPasswordShouldThrowException() throws DaoException {
        String password = "12345678Abc";
        given(userDao.updatePassword(anyLong(), anyString())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> userService.changeUserPassword(DIGIT_ONE_LONG, password));
    }

    @Test
    void findUsersByStatusShouldReturnFilled() throws DaoException, ServiceException {
        User user = createUser();
        List<User> users = List.of(createUser());
        given(userDao.findUsersByStatus(any(UserStatus.class), anyInt(), anyInt())).willReturn(users);
        assertEquals(userService.findUsersByStatus(user.getStatus(), DIGIT_ONE_STR, DIGIT_TEN_INT), users);
    }

    @Test
    void findUsersByStatusShouldReturnEmpty() throws DaoException, ServiceException {
        User user = createUser();
        given(userDao.findUsersByStatus(any(UserStatus.class), anyInt(), anyInt())).willReturn(new ArrayList<>());
        assertThat(userService.findUsersByStatus(user.getStatus(), DIGIT_ONE_STR, DIGIT_TEN_INT)).isEmpty();
    }

    @Test
    void findUsersByStatusShouldThrowException() throws DaoException {
        User user = createUser();
        given(userDao.findUsersByStatus(any(UserStatus.class), anyInt(), anyInt())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> userService.findUsersByStatus(user.getStatus(), DIGIT_ONE_STR, DIGIT_TEN_INT));
    }

    @Test
    void updateUserStatusShouldReturnTrue() throws DaoException, ServiceException {
        UserStatus status = UserStatus.ACTIVE;
        given(userDao.updateUserStatus(anyLong(), any(UserStatus.class))).willReturn(true);
        assertTrue(userService.updateUserStatus(DIGIT_ONE_STR, status));
    }

    @Test
    void updateUserStatusShouldReturnFalse() throws DaoException, ServiceException {
        UserStatus status = UserStatus.ACTIVE;
        given(userDao.updateUserStatus(anyLong(), any(UserStatus.class))).willReturn(false);
        assertFalse(userService.updateUserStatus(DIGIT_ONE_STR, status));
    }

    @Test
    void updateUserStatusShouldThrowException() throws DaoException {
        UserStatus status = UserStatus.ACTIVE;
        given(userDao.updateUserStatus(anyLong(), any(UserStatus.class))).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> userService.updateUserStatus(DIGIT_ONE_STR, status));

    }

    @Test
    void countAllUsersShouldReturnValue() throws DaoException, ServiceException {
        given(userDao.countAllUsers()).willReturn(DIGIT_TEN_INT);
        assertEquals(DIGIT_TEN_INT, userService.countAllUsers());
    }

    @Test
    void countAllUsersShouldThrowException() throws DaoException {
        given(userDao.countAllUsers()).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> userService.countAllUsers());
    }

    @Test
    void countAllUsersByStatusShouldReturnValue() throws DaoException, ServiceException {
        UserStatus status = UserStatus.ACTIVE;
        given(userDao.countAllUsersByStatus(any(UserStatus.class))).willReturn(DIGIT_TEN_INT);
        assertEquals(DIGIT_TEN_INT, userService.countAllUsersByStatus(status));
    }

    @Test
    void countAllUsersByStatusShouldThrowException() throws DaoException {
        UserStatus status = UserStatus.ACTIVE;
        given(userDao.countAllUsersByStatus(any(UserStatus.class))).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> userService.countAllUsersByStatus(status));
    }

    @Test
    void searchUsersShouldReturnResult() throws DaoException, ServiceException {
        List<User> users = List.of(createUser());
        given(userDao.searchUsers(anyString())).willReturn(users);
        assertEquals(userService.searchUsers(DIGIT_ONE_STR), users);
    }

    @Test
    void searchUsersShouldThrowException() throws DaoException {
        given(userDao.searchUsers(anyString())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> userService.searchUsers(DIGIT_ONE_STR));
    }
}