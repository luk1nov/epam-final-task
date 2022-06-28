package by.lukyanov.finaltask.model.service.impl;

import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.dao.impl.CarDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.lukyanov.finaltask.factory.CarFactory.createCar;
import static by.lukyanov.finaltask.factory.CarFactory.createCarData;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.openMocks;

class CarServiceImplTest {
    private static final int DIGIT_ZERO_INT = 0;
    private static final String DIGIT_TEN_STR = "10";

    @Mock
    private CarDaoImpl carDao;

    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void addCarShouldReturnTrue() throws DaoException, ServiceException {
        InputStream inputStream = new ByteArrayInputStream(new byte[64]);
        given(carDao.insert(any(Car.class), any(InputStream.class))).willReturn(true);
        assertTrue(carService.addCar(createCarData(), inputStream));
    }

    @Test
    void addCarShouldReturnFalse() throws DaoException, ServiceException {
        InputStream inputStream = new ByteArrayInputStream(new byte[64]);
        given(carDao.insert(any(Car.class), any(InputStream.class))).willReturn(false);
        assertFalse(carService.addCar(createCarData(), inputStream));
    }

    @Test
    void addCarShouldReturnThrowException() throws DaoException {
        InputStream inputStream = new ByteArrayInputStream(new byte[64]);
        given(carDao.insert(any(Car.class), any(InputStream.class))).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> carService.addCar(createCarData(), inputStream));
    }

    @Test
    void findAllCarsShouldReturnFilled() throws DaoException, ServiceException {
        List<Car> cars = List.of(createCar());
        given(carDao.findAll(anyInt(), anyInt())).willReturn(cars);
        assertEquals(cars, carService.findAllCars(DIGIT_TEN_STR, DIGIT_ZERO_INT));
    }

    @Test
    void findAllCarsShouldReturnEmpty() throws DaoException, ServiceException {
        given(carDao.findAll(anyInt(), anyInt())).willReturn(new ArrayList<>());
        assertThat(carService.findAllCars(DIGIT_TEN_STR, DIGIT_ZERO_INT)).isEmpty();
    }

    @Test
    void findAllCarsShouldThrowException() throws DaoException {
        given(carDao.findAll(anyInt(), anyInt())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> carService.findAllCars(DIGIT_TEN_STR, DIGIT_ZERO_INT));
    }

    @Test
    void findCarByIdShouldExists() throws DaoException, ServiceException {
        Car car = createCar();
        given(carDao.findCarById(anyLong())).willReturn(Optional.of(car));
        assertThat(carService.findCarById(String.valueOf(car.getId()))).isPresent()
                .get()
                .isEqualTo(car);
    }

    @Test
    void findCarByIdShouldNotExists() throws DaoException, ServiceException {
        given(carDao.findCarById(anyLong())).willReturn(Optional.empty());
        assertThat(carService.findCarById(DIGIT_TEN_STR)).isNotPresent();
    }

    @Test
    void findCarByIdShouldThrowException() throws DaoException {
        given(carDao.findCarById(anyLong())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> carService.findCarById(DIGIT_TEN_STR));
    }

    @Test
    void updateCarShouldReturnTrue() throws DaoException, ServiceException {
        InputStream inputStream = new ByteArrayInputStream(new byte[64]);
        given(carDao.update(any(Car.class))).willReturn(true);
        assertTrue(carService.updateCar(createCarData(), inputStream));
    }

    @Test
    void updateCarShouldReturnFalse() throws DaoException, ServiceException {
        InputStream inputStream = new ByteArrayInputStream(new byte[64]);
        given(carDao.update(any(Car.class))).willReturn(false);
        assertFalse(carService.updateCar(createCarData(), inputStream));
    }

    @Test
    void updateCarShouldThrowException() throws DaoException {
        InputStream inputStream = new ByteArrayInputStream(new byte[64]);
        given(carDao.update(any(Car.class))).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> carService.updateCar(createCarData(), inputStream));
    }

    @Test
    void findCarsByCategoryIdShouldReturnFilled() throws DaoException, ServiceException {
        List<Car> cars = List.of(createCar());
        given(carDao.findAll(anyInt(), anyInt())).willReturn(cars);
        assertEquals(carService.findAllCars(DIGIT_TEN_STR, DIGIT_ZERO_INT), cars);
    }

    @Test
    void findCarsByCategoryIdShouldReturnEmpty() throws DaoException, ServiceException {
        given(carDao.findAll(anyInt(), anyInt())).willReturn(new ArrayList<>());
        assertThat(carService.findAllCars(DIGIT_TEN_STR, DIGIT_ZERO_INT)).isEmpty();
    }

    @Test
    void findCarsByCategoryIdShouldThrowException() throws DaoException {
        given(carDao.findAll(anyInt(), anyInt())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> carService.findAllCars(DIGIT_TEN_STR, DIGIT_ZERO_INT));
    }

    @Test
    void deleteCarByIdShouldReturnTrue() throws DaoException, ServiceException {
        given(carDao.delete(anyLong())).willReturn(true);
        assertTrue(carService.deleteCarById(DIGIT_TEN_STR));
    }

    @Test
    void deleteCarByIdShouldReturnFalse() throws DaoException, ServiceException {
        given(carDao.delete(anyLong())).willReturn(false);
        assertFalse(carService.deleteCarById(DIGIT_TEN_STR));
    }

    @Test
    void deleteCarByIdShouldThrowException() throws DaoException {
        given(carDao.delete(anyLong())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> carService.deleteCarById(DIGIT_TEN_STR));
    }

    @Test
    void changeCarActiveShouldReturnTrue() throws DaoException, ServiceException {
        String trueStr = "true";
        given(carDao.changeCarActiveById(anyLong(), anyBoolean())).willReturn(true);
        assertTrue(carService.changeCarActive(DIGIT_TEN_STR, trueStr));
    }

    @Test
    void changeCarActiveShouldReturnFalse() throws DaoException, ServiceException {
        String trueStr = "true";
        given(carDao.changeCarActiveById(anyLong(), anyBoolean())).willReturn(false);
        assertFalse(carService.changeCarActive(DIGIT_TEN_STR, trueStr));
    }

    @Test
    void changeCarActiveShouldThrowException() throws DaoException {
        String trueStr = "true";
        given(carDao.changeCarActiveById(anyLong(), anyBoolean())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> carService.changeCarActive(DIGIT_TEN_STR, trueStr));
    }

    @Test
    void findCarsByActiveStatusShouldReturnFilled() throws DaoException, ServiceException {
        List<Car> cars = List.of(createCar());
        given(carDao.findCarsByActive(anyBoolean(), anyInt(), anyInt())).willReturn(cars);
        assertEquals(carService.findCarsByActiveStatus(true, DIGIT_TEN_STR, DIGIT_ZERO_INT), cars);
    }

    @Test
    void findCarsByActiveStatusShouldReturnEmpty() throws DaoException, ServiceException {
        given(carDao.findCarsByActive(anyBoolean(), anyInt(), anyInt())).willReturn(new ArrayList<>());
        assertThat(carService.findCarsByActiveStatus(true, DIGIT_TEN_STR, DIGIT_ZERO_INT)).isEmpty();
    }

    @Test
    void findCarsByActiveStatusShouldThrowException() throws DaoException {
        given(carDao.findCarsByActive(anyBoolean(), anyInt(), anyInt())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> carService.findCarsByActiveStatus(true, DIGIT_TEN_STR, DIGIT_ZERO_INT));
    }

    @Test
    void countAllCarsShouldReturnResult() throws DaoException, ServiceException {
        given(carDao.countAllCars()).willReturn(DIGIT_ZERO_INT);
        assertEquals(carService.countAllCars(), DIGIT_ZERO_INT);
    }

    @Test
    void countAllCarsShouldThrowException() throws DaoException {
        given(carDao.countAllCars()).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> carService.countAllCars());
    }

    @Test
    void countAllCarsByActiveShouldReturnResult() throws DaoException, ServiceException {
        given(carDao.countAllCarsByActive(anyBoolean())).willReturn(DIGIT_ZERO_INT);
        assertEquals(carService.countAllCarsByActive(true), DIGIT_ZERO_INT);
    }

    @Test
    void countAllCarsByActiveShouldThrowException() throws DaoException {
        given(carDao.countAllCarsByActive(anyBoolean())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> carService.countAllCarsByActive(true));
    }

    @Test
    void countAllCarsByCategoryIdShouldReturnResult() throws DaoException, ServiceException {
        long categoryId = 1L;
        given(carDao.countAllCarsByCategoryId(anyLong())).willReturn(DIGIT_ZERO_INT);
        assertEquals(carService.countAllCarsByCategoryId(categoryId), DIGIT_ZERO_INT);
    }

    @Test
    void countAllCarsByCategoryIdShouldThrowException() throws DaoException {
        long categoryId = 1L;
        given(carDao.countAllCarsByCategoryId(anyLong())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> carService.countAllCarsByCategoryId(categoryId));
    }

    @Test
    void searchCarsShouldReturnFilled() throws DaoException, ServiceException {
        List<Car> cars = List.of(createCar());
        given(carDao.searchCars(anyString())).willReturn(cars);
        assertEquals(carService.searchCars(DIGIT_TEN_STR), cars);
    }

    @Test
    void searchCarsShouldReturnEmpty() throws DaoException, ServiceException {
        given(carDao.searchCars(anyString())).willReturn(new ArrayList<>());
        assertThat(carService.searchCars(DIGIT_TEN_STR)).isEmpty();
    }

    @Test
    void searchCarsShouldThrowException() throws DaoException {
        given(carDao.searchCars(anyString())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> carService.searchCars(DIGIT_TEN_STR));
    }

    @Test
    void findCarByVinCodeShouldExists() throws DaoException, ServiceException {
        Car car = createCar();
        given(carDao.findCarByVinCode(anyString())).willReturn(Optional.of(car));
        assertThat(carService.findCarByVinCode(car.getVinCode())).isPresent()
                .get()
                .isEqualTo(car);
    }
    @Test
    void findCarByVinCodeShouldNotExists() throws DaoException, ServiceException {
        Car car = createCar();
        given(carDao.findCarByVinCode(anyString())).willReturn(Optional.empty());
        assertThat(carService.findCarByVinCode(car.getVinCode())).isNotPresent();
    }

    @Test
    void findCarByVinCodeShouldThrowException() throws DaoException {
        Car car = createCar();
        given(carDao.findCarByVinCode(anyString())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> carService.findCarByVinCode(car.getVinCode()));
    }
}