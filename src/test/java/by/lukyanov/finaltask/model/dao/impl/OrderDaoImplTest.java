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

class OrderDaoImplTest {
    private static Connection connection;
    private static Order firstOrder;
    private static Order secondOrder;

    @Mock
    private ConnectionPool connectionPool;

    @InjectMocks
    private OrderDaoImpl orderDao;

    @BeforeEach
    void setUp() {
        openMocks(this);
        TestConnectionConfig config = new TestConnectionConfig();
        connection = config.getConnection();
        config.updateDatabase(connection);
        firstOrder = createOrder();
        secondOrder = new Order.OrderBuilder()
                .id(2L)
                .beginDate(LocalDate.parse("2022-07-05"))
                .endDate(LocalDate.parse("2022-07-06"))
                .car(createCar())
                .user(createUser())
                .orderStatus(OrderStatus.ACTIVE)
                .price(new BigDecimal(1000))
                .report(new OrderReport("photo", "text", OrderReportStatus.WITHOUT_DEFECTS))
                .build();
        when(connectionPool.getConnection()).thenReturn(connection);
    }

    @Test
    void insert() throws DaoException {
        assertTrue(orderDao.insert(secondOrder));
    }

    @Test
    void delete() throws DaoException {
        assertTrue(orderDao.delete(firstOrder.getId()));
    }

    @Test
    void findAll() throws DaoException {
        assertThat(orderDao.findAll(100, 0))
                .filteredOn("id",  firstOrder.getId())
                .hasSize(1);
    }

    @Test
    void update() throws DaoException {
        assertTrue(orderDao.update(firstOrder));
    }

    @Test
    void findOrdersByUserId() throws DaoException {
        assertThat(orderDao.findOrdersByUserId(firstOrder.getUser().getId(), 100, 0))
                .filteredOn(order -> order.getId() == firstOrder.getId())
                .hasSize(1);
    }

    @Test
    void findActiveOrderDatesByCarId() throws DaoException {
        assertThat(orderDao.findActiveOrderDatesByCarId(secondOrder.getCar().getId()))
                .filteredOn("beginDate", secondOrder.getBeginDate())
                .filteredOn("endDate", secondOrder.getEndDate())
                .hasSize(1);
    }

    @Test
    void checkCarAvailabilityByDateRange() throws DaoException {
        assertFalse(orderDao.checkCarAvailabilityByDateRange(secondOrder.getBeginDate(), secondOrder.getEndDate(), secondOrder.getCar().getId()));
    }

    @Test
    void findOrdersByOrderStatus() throws DaoException {
        assertThat(orderDao.findOrdersByOrderStatus(firstOrder.getOrderStatus(), 100, 0))
                .filteredOn("id", firstOrder.getId())
                .hasSize(1);
    }

    @Test
    void findById() throws DaoException {
        assertThat(orderDao.findById(firstOrder.getId())).isPresent()
                .get()
                .extracting("id", "orderStatus")
                .doesNotContainNull()
                .containsExactly(firstOrder.getId(), firstOrder.getOrderStatus());
    }

    @Test
    void cancelOrder() throws DaoException {
        assertTrue(orderDao.cancelOrder(firstOrder));
    }

    @Test
    void completeOrder() throws DaoException {
        assertTrue(orderDao.completeOrder(secondOrder, new ByteArrayInputStream(new byte[64])));
    }

    @Test
    void findOrderReportById() throws DaoException {
        assertThat(orderDao.findOrderReportById(firstOrder.getId()))
                .isPresent();
    }

    @Test
    void countAllOrders() throws DaoException {
        assertEquals(2, orderDao.countAllOrders());
    }

    @Test
    void countOrdersByStatus() throws DaoException {
        assertEquals(1, orderDao.countOrdersByStatus(firstOrder.getOrderStatus()));
    }

    @Test
    void countOrdersByUserId() throws DaoException {
        assertEquals(2, orderDao.countOrdersByUserId(firstOrder.getUser().getId()));
    }

    @Test
    void searchOrders() throws DaoException {
        assertThat(orderDao.searchOrders(String.valueOf(firstOrder.getId())))
                .filteredOn("id", firstOrder.getId())
                .hasSize(1);
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