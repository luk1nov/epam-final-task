package by.lukyanov.finaltask.model.dao.impl;

import by.lukyanov.finaltask.entity.*;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.model.connection.ConnectionPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import static by.lukyanov.finaltask.factory.CarFactory.createCar;
import static by.lukyanov.finaltask.factory.OrderFactory.createOrder;
import static by.lukyanov.finaltask.factory.UserFactory.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class UserDaoImplTest {
    private static Connection connection;
    private static User firstUser;
    private static User secondUser;

    @Mock
    private ConnectionPool connectionPool;

    @InjectMocks
    private UserDaoImpl userDao;


    @BeforeEach
    void setUp() {
        openMocks(this);
        TestConnectionConfig config = new TestConnectionConfig();
        connection = config.getConnection();
        config.updateDatabase(connection);
        firstUser = createUser();
        secondUser = new User.UserBuilder()
                .id(2L)
                .email("dsa@gmail.com")
                .name("Alex")
                .surname("Petrov")
                .role(UserRole.USER)
                .status(UserStatus.INACTIVE)
                .password("Asd123456")
                .phone("+375-29-570-07-66")
                .balance(new BigDecimal(0))
                .build();
        when(connectionPool.getConnection()).thenReturn(connection);
    }

    @Test
    void insert() throws DaoException {
        User newUser = new User.UserBuilder()
                .email("test@gmail.com")
                .name("Alex")
                .surname("Wingman")
                .role(UserRole.USER)
                .status(UserStatus.INACTIVE)
                .password("Asd123456")
                .phone("+375-29-570-07-66")
                .balance(new BigDecimal(0))
                .build();
        assertTrue(userDao.insert(newUser));
    }

    @Test
    void delete() throws DaoException {
        assertTrue(userDao.delete(secondUser.getId()));
    }

    @Test
    void findAll() throws DaoException {
        assertThat(userDao.findAll(100, 0))
                .filteredOn("email", in(firstUser.getEmail(), secondUser.getEmail()))
                .hasSize(2);
    }

    @Test
    void update() throws DaoException {
        assertTrue(userDao.update(firstUser));
    }

    @Test
    void findUserByEmail() throws DaoException {
        assertThat(userDao.findUserByEmail(firstUser.getEmail()))
                .isPresent()
                .map(User::getId)
                .hasValue(firstUser.getId());
    }

    @Test
    void findUserById() throws DaoException {
        assertThat(userDao.findUserById(firstUser.getId()))
                .isPresent()
                .map(User::getId)
                .hasValue(firstUser.getId());
    }

    @Test
    void findUserByPhone() throws DaoException {
        assertThat(userDao.findUserByPhone(secondUser.getPhone()))
                .isPresent()
                .map(User::getId)
                .hasValue(secondUser.getId());
    }

    @Test
    void updateBalance() throws DaoException {
        assertTrue(userDao.updateBalance(firstUser.getId(), new BigDecimal(10), true));
    }

    @Test
    void updateUserInfo() throws DaoException {
        assertTrue(userDao.updateUserInfo(firstUser));
    }

    @Test
    void updateDriverLicense() throws DaoException {
        assertTrue(userDao.updateDriverLicense(firstUser.getId(), new ByteArrayInputStream(new byte[64])));
    }

    @Test
    void updatePassword() throws DaoException {
        assertTrue(userDao.updatePassword(firstUser.getId(), firstUser.getPassword()));
    }

    @Test
    void findUsersByStatus() throws DaoException {
        assertThat(userDao.findUsersByStatus(firstUser.getStatus(), 100, 0))
                .extracting("id")
                .containsExactly(firstUser.getId());
    }

    @Test
    void updateUserStatus() throws DaoException {
        assertTrue(userDao.updateUserStatus(firstUser.getId(), firstUser.getStatus()));
    }

    @Test
    void countAllUsers() throws DaoException {
        assertEquals(2, userDao.countAllUsers());
    }

    @Test
    void countAllUsersByStatus() throws DaoException {
        assertEquals(1, userDao.countAllUsersByStatus(firstUser.getStatus()));
    }

    @Test
    void searchUsers() throws DaoException {
        assertThat(userDao.searchUsers(firstUser.getEmail()))
                .extracting("id")
                .containsExactly(firstUser.getId());
    }

    @AfterEach
    void tearDown() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}