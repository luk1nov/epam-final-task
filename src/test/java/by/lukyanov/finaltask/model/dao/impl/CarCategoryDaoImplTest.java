package by.lukyanov.finaltask.model.dao.impl;

import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.model.connection.ConnectionPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class CarCategoryDaoImplTest {
    private static Connection connection;
    private static CarCategory firstCategory;
    private static CarCategory secondCategory;

    @Mock
    private ConnectionPool connectionPool;

    @InjectMocks
    private CarCategoryDaoImpl carCategoryDao;

    @BeforeEach
    void setUp() {
        openMocks(this);
        TestConnectionConfig config = new TestConnectionConfig();
        connection = config.getConnection();
        config.updateDatabase(connection);
        firstCategory = new CarCategory(1L, "Cars");
        secondCategory = new CarCategory(2L, "Premium");
        when(connectionPool.getConnection()).thenReturn(connection);
    }

    @Test
    void insert() throws DaoException {
        CarCategory newCategory = new CarCategory("motorcycles");
        assertTrue(carCategoryDao.insert(newCategory));
    }

    @Test
    void delete() throws DaoException {
        assertTrue(carCategoryDao.delete(secondCategory.getId()));
    }

    @Test
    void findAll() throws DaoException {
        List<CarCategory> carCategories = List.of(firstCategory, secondCategory);
        assertEquals(carCategories, carCategoryDao.findAll(100, 0));
    }

    @Test
    void update() throws DaoException {
        assertTrue(carCategoryDao.update(secondCategory));
    }

    @Test
    void findById() throws DaoException {
        assertThat(carCategoryDao.findById(firstCategory.getId())).isPresent()
                .get()
                .isEqualTo(firstCategory);
    }

    @Test
    void findCarCategoryByTitle() throws DaoException {
        assertThat(carCategoryDao.findCarCategoryByTitle(firstCategory.getTitle())).isPresent()
                .get()
                .isEqualTo(firstCategory);
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