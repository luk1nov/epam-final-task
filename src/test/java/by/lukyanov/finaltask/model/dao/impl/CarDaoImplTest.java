package by.lukyanov.finaltask.model.dao.impl;

import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.entity.CarInfo;
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

import static by.lukyanov.finaltask.factory.CarFactory.createCar;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class CarDaoImplTest {
    private static Connection connection;
    private static Car firstCar;
    private static Car secondCar;

    @Mock
    private ConnectionPool connectionPool;

    @InjectMocks
    private CarDaoImpl carDao;

    @BeforeEach
    void setUp() {
        openMocks(this);
        TestConnectionConfig config = new TestConnectionConfig();
        connection = config.getConnection();
        config.updateDatabase(connection);
        firstCar = createCar();
        secondCar = new Car.CarBuilder()
                .id(2L)
                .brand("BMW")
                .model("M8")
                .vin("ZFA13533698421778")
                .regularPrice(new BigDecimal(240))
                .active(true)
                .category(new CarCategory(1L))
                .carInfo(new CarInfo(4.3, 745, CarInfo.Drivetrain.RWD))
                .build();
        when(connectionPool.getConnection()).thenReturn(connection);
    }

    @Test
    void delete() throws DaoException {
        assertTrue(carDao.delete(secondCar.getId()));
    }

    @Test
    void findAll() throws DaoException {
        assertThat(carDao.findAll(100, 0))
                .filteredOn(car -> car.getVinCode().equals(firstCar.getVinCode())
                        || car.getVinCode().equals(secondCar.getVinCode()))
                .hasSize(2);
    }

    @Test
    void update() throws DaoException {
        assertTrue(carDao.update(firstCar));
    }

    @Test
    void findCarById() throws DaoException {
        assertThat(carDao.findCarById(firstCar.getId())).isPresent()
                .get()
                .extracting("id", "vinCode")
                .doesNotContainNull()
                .containsExactly(firstCar.getId(), firstCar.getVinCode());
    }

    @Test
    void insert() throws DaoException {
        Car newCar = new Car.CarBuilder()
                .id(3L)
                .brand("Mercedes")
                .model("EQS")
                .vin("ZFA13533695321778")
                .regularPrice(new BigDecimal(200))
                .active(true)
                .category(new CarCategory(1L))
                .carInfo(new CarInfo(4.3, 745, CarInfo.Drivetrain.RWD))
                .build();
        assertTrue(carDao.insert(newCar, new ByteArrayInputStream(new byte[0])));
    }

    @Test
    void updateWithImage() throws DaoException {
        assertTrue(carDao.updateWithImage(firstCar, new ByteArrayInputStream(new byte[0])));
    }

    @Test
    void findCarsByCategoryId() throws DaoException {
        assertThat(carDao.findCarsByCategoryId(firstCar.getCarCategory().getId(), 100, 0))
                .filteredOn(car -> car.getVinCode().equals(firstCar.getVinCode())
                        || car.getVinCode().equals(secondCar.getVinCode()))
                .hasSize(2);
    }

    @Test
    void changeCarActiveById() throws DaoException {
        assertTrue(carDao.changeCarActiveById(firstCar.getId(), false));
    }

    @Test
    void findCarsByActive() throws DaoException {
        assertThat(carDao.findCarsByActive(true, 100, 0))
                .filteredOn(car -> car.getVinCode().equals(firstCar.getVinCode()))
                .hasSize(1);
    }

    @Test
    void countAllCars() throws DaoException {
        assertEquals(2, carDao.countAllCars());
    }

    @Test
    void countAllCarsByActive() throws DaoException {
        assertEquals(1, carDao.countAllCarsByActive(true));
    }

    @Test
    void countAllCarsByCategoryId() throws DaoException {
        assertEquals(2, carDao.countAllCarsByCategoryId(firstCar.getCarCategory().getId()));
    }

    @Test
    void searchCars() throws DaoException {
        assertThat(carDao.searchCars(firstCar.getVinCode()))
                .filteredOn(car -> car.getVinCode().equals(firstCar.getVinCode()))
                .hasSize(1);
    }

    @Test
    void findCarByVinCode() throws DaoException {
        assertThat(carDao.findCarByVinCode(firstCar.getVinCode())).isPresent()
                .get()
                .extracting("id", "vinCode")
                .doesNotContainNull()
                .containsExactly(firstCar.getId(), firstCar.getVinCode());
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