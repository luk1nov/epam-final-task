package by.lukyanov.finaltask.model.service.impl;

import by.lukyanov.finaltask.entity.Order;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.dao.impl.OrderDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static by.lukyanov.finaltask.factory.OrderFactory.createOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.openMocks;

class OrderServiceImplTest {
    private static final int DIGIT_ZERO_INT = 0;
    private static final String DIGIT_TEN_STR = "10";

    @Mock
    private OrderDaoImpl orderDao;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void findAllOrdersShouldReturnFilled() throws ServiceException, DaoException {
        List<Order> orders = List.of(createOrder());
        given(orderDao.findAll(anyInt(), anyInt())).willReturn(orders);
        assertEquals(orderService.findAllOrders(DIGIT_TEN_STR, DIGIT_ZERO_INT), orders);
    }

    @Test
    void findAllOrdersShouldReturnEmpty() throws ServiceException, DaoException {
        given(orderDao.findAll(anyInt(), anyInt())).willReturn(new ArrayList<>());
        assertThat(orderService.findAllOrders(DIGIT_TEN_STR, DIGIT_ZERO_INT)).isEmpty();
    }

    @Test
    void findAllOrdersShouldThrowException() throws DaoException {
        given(orderDao.findAll(anyInt(), anyInt())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> orderService.findAllOrders(DIGIT_TEN_STR, DIGIT_ZERO_INT));
    }

    @Test
    void findAllOrdersByUserId() {
    }

    @Test
    void findActiveOrderDatesByCarId() {
    }

    @Test
    void addOrder() {
    }

    @Test
    void findOrdersByOrderStatus() {
    }

    @Test
    void findOrderById() {
    }

    @Test
    void updateOrderStatus() {
    }

    @Test
    void cancelOrder() {
    }

    @Test
    void testCancelOrder() {
    }

    @Test
    void deleteOrder() {
    }

    @Test
    void completeOrder() {
    }

    @Test
    void findOrderReportById() {
    }

    @Test
    void countAllOrders() {
    }

    @Test
    void countOrdersByStatus() {
    }

    @Test
    void countOrdersByUserId() {
    }

    @Test
    void searchOrders() {
    }

    @Test
    void calculateOrderPrice() {
    }
}