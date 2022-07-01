package by.lukyanov.finaltask.model.service.impl;

import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.entity.Order;
import by.lukyanov.finaltask.entity.OrderReport;
import by.lukyanov.finaltask.entity.OrderStatus;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.dao.impl.OrderDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.lukyanov.finaltask.factory.OrderFactory.createOrder;
import static by.lukyanov.finaltask.factory.OrderReportFactory.createOrderReport;
import static by.lukyanov.finaltask.factory.OrderReportFactory.createReportData;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.openMocks;

class OrderServiceImplTest {
    private static final int DIGIT_FIVE_INT = 5;
    private static final String DIGIT_TEN_STR = "10";
    private static final long DIGIT_ONE_LONG = 1L;
    private static final String dateRange = "2022-07-01";
    private static final String MESSAGE = "message";

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
        assertEquals(orderService.findAllOrders(DIGIT_TEN_STR, DIGIT_FIVE_INT), orders);
    }

    @Test
    void findAllOrdersShouldReturnEmpty() throws ServiceException, DaoException {
        given(orderDao.findAll(anyInt(), anyInt())).willReturn(new ArrayList<>());
        assertThat(orderService.findAllOrders(DIGIT_TEN_STR, DIGIT_FIVE_INT)).isEmpty();
    }

    @Test
    void findAllOrdersShouldThrowException() throws DaoException {
        given(orderDao.findAll(anyInt(), anyInt())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> orderService.findAllOrders(DIGIT_TEN_STR, DIGIT_FIVE_INT));
    }

    @Test
    void findAllOrdersByUserIdShouldReturnFilled() throws DaoException, ServiceException {
        List<Order> orders = List.of(createOrder());
        given(orderDao.findOrdersByUserId(anyLong(), anyInt(), anyInt())).willReturn(orders);
        assertEquals(orders, orderService.findAllOrdersByUserId(DIGIT_ONE_LONG, DIGIT_TEN_STR, DIGIT_FIVE_INT));
    }

    @Test
    void findAllOrdersByUserIdShouldReturnEmpty() throws DaoException, ServiceException {
        given(orderDao.findOrdersByUserId(anyLong(), anyInt(), anyInt())).willReturn(new ArrayList<>());
        assertThat(orderService.findAllOrdersByUserId(DIGIT_ONE_LONG, DIGIT_TEN_STR, DIGIT_FIVE_INT)).isEmpty();
    }

    @Test
    void findAllOrdersByUserIdShouldThrowException() throws DaoException {
        given(orderDao.findOrdersByUserId(anyLong(), anyInt(), anyInt())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> orderService.findAllOrdersByUserId(DIGIT_ONE_LONG, DIGIT_TEN_STR, DIGIT_FIVE_INT));
    }

    @Test
    void findActiveOrderDatesByCarIdShouldReturnFilled() throws DaoException, ServiceException {
        List<Order> orders = List.of(createOrder());
        given(orderDao.findActiveOrderDatesByCarId(anyLong())).willReturn(orders);
        assertEquals(orders, orderService.findActiveOrderDatesByCarId(DIGIT_TEN_STR));
    }

    @Test
    void findActiveOrderDatesByCarIdShouldReturnEmpty() throws DaoException, ServiceException {
        given(orderDao.findActiveOrderDatesByCarId(anyLong())).willReturn(new ArrayList<>());
        assertThat(orderService.findActiveOrderDatesByCarId(DIGIT_TEN_STR)).isEmpty();
    }

    @Test
    void findActiveOrderDatesByCarIdShouldThrowException() throws DaoException {
        given(orderDao.findActiveOrderDatesByCarId(anyLong())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> orderService.findActiveOrderDatesByCarId(DIGIT_TEN_STR));
    }

    @Test
    void addOrderShouldReturnTrue() throws DaoException, ServiceException {
        Order order = createOrder();
        given(orderDao.checkCarAvailabilityByDateRange(any(LocalDate.class), any(LocalDate.class), anyLong())).willReturn(true);
        given(orderDao.create(any(Order.class))).willReturn(true);
        assertTrue(orderService.addOrder(order, dateRange));
    }

    @Test
    void addOrderShouldReturnFalse() throws DaoException, ServiceException {
        Order order = createOrder();
        given(orderDao.checkCarAvailabilityByDateRange(any(LocalDate.class), any(LocalDate.class), anyLong())).willReturn(true);
        given(orderDao.create(any(Order.class))).willReturn(false);
        assertFalse(orderService.addOrder(order, dateRange));
    }

    @Test
    void addOrderShouldThrowException() throws DaoException {
        Order order = createOrder();
        given(orderDao.checkCarAvailabilityByDateRange(any(LocalDate.class), any(LocalDate.class), anyLong())).willReturn(true);
        given(orderDao.create(any(Order.class))).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> orderService.addOrder(order, dateRange));
    }

    @Test
    void findOrdersByOrderStatusShouldReturnFilled() throws DaoException, ServiceException {
        List<Order> orders = List.of(createOrder());
        given(orderDao.findOrdersByOrderStatus(any(OrderStatus.class), anyInt(), anyInt())).willReturn(orders);
        assertEquals(orders, orderService.findOrdersByOrderStatus(OrderStatus.ACTIVE, DIGIT_TEN_STR, DIGIT_FIVE_INT));
    }

    @Test
    void findOrdersByOrderStatusShouldReturnEmpty() throws DaoException, ServiceException {
        given(orderDao.findOrdersByOrderStatus(any(OrderStatus.class), anyInt(), anyInt())).willReturn(new ArrayList<>());
        assertThat(orderService.findOrdersByOrderStatus(OrderStatus.ACTIVE, DIGIT_TEN_STR, DIGIT_FIVE_INT)).isEmpty();
    }

    @Test
    void findOrdersByOrderStatusShouldThrowException() throws DaoException {
        given(orderDao.findOrdersByOrderStatus(any(OrderStatus.class), anyInt(), anyInt())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> orderService.findOrdersByOrderStatus(OrderStatus.ACTIVE, DIGIT_TEN_STR, DIGIT_FIVE_INT));
    }

    @Test
    void findOrderByIdShouldExists() throws DaoException, ServiceException {
        Order order = createOrder();
        given(orderDao.findById(anyLong())).willReturn(Optional.of(order));
        assertThat(orderService.findOrderById(DIGIT_TEN_STR)).isPresent()
                .get()
                .isEqualTo(order);
    }

    @Test
    void findOrderByIdShouldNotExists() throws DaoException, ServiceException {
        given(orderDao.findById(anyLong())).willReturn(Optional.empty());
        assertThat(orderService.findOrderById(DIGIT_TEN_STR)).isNotPresent();
    }

    @Test
    void findOrderByIdShouldThrowException() throws DaoException {
        given(orderDao.findById(anyLong())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> orderService.findOrderById(DIGIT_TEN_STR));
    }

    @Test
    void updateOrderStatusShouldReturnTrue() throws ServiceException, DaoException {
        given(orderDao.update(any(Order.class))).willReturn(true);
        assertTrue(orderService.updateOrderStatus(OrderStatus.ACTIVE, DIGIT_TEN_STR));
    }

    @Test
    void updateOrderStatusShouldReturnFalse() throws ServiceException, DaoException {
        given(orderDao.update(any(Order.class))).willReturn(false);
        assertFalse(orderService.updateOrderStatus(OrderStatus.ACTIVE, DIGIT_TEN_STR));
    }

    @Test
    void updateOrderStatusShouldThrowException() throws DaoException {
        given(orderDao.update(any(Order.class))).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> orderService.updateOrderStatus(OrderStatus.ACTIVE, DIGIT_TEN_STR));
    }

    @Test
    void cancelOrderWithoutMessageShouldReturnTrue() throws ServiceException, DaoException {
        given(orderDao.cancelOrder(any(Order.class))).willReturn(true);
        given(orderDao.findById(anyLong())).willReturn(Optional.of(createOrder()));
        assertTrue(orderService.cancelOrder(DIGIT_TEN_STR));
    }

    @Test
    void cancelOrderWithoutMessageShouldReturnFalse() throws ServiceException, DaoException {
        given(orderDao.cancelOrder(any(Order.class))).willReturn(false);
        given(orderDao.findById(anyLong())).willReturn(Optional.of(createOrder()));
        assertFalse(orderService.cancelOrder(DIGIT_TEN_STR));
    }

    @Test
    void cancelOrderWithoutMessageShouldThrowException() throws DaoException {
        given(orderDao.cancelOrder(any(Order.class))).willThrow(DaoException.class);
        given(orderDao.findById(anyLong())).willReturn(Optional.of(createOrder()));
        assertThrows(ServiceException.class, () -> orderService.cancelOrder(DIGIT_TEN_STR));
    }

    @Test
    void cancelOrderWithMessageShouldReturnTrue() throws ServiceException, DaoException {
        given(orderDao.cancelOrder(any(Order.class))).willReturn(true);
        given(orderDao.findById(anyLong())).willReturn(Optional.of(createOrder()));
        assertTrue(orderService.cancelOrder(DIGIT_TEN_STR, MESSAGE));
    }

    @Test
    void cancelOrderWithMessageShouldReturnFalse() throws ServiceException, DaoException {
        given(orderDao.cancelOrder(any(Order.class))).willReturn(false);
        given(orderDao.findById(anyLong())).willReturn(Optional.of(createOrder()));
        assertFalse(orderService.cancelOrder(DIGIT_TEN_STR, MESSAGE));
    }

    @Test
    void cancelOrderWithMessageShouldThrowException() throws DaoException {
        given(orderDao.cancelOrder(any(Order.class))).willThrow(DaoException.class);
        given(orderDao.findById(anyLong())).willReturn(Optional.of(createOrder()));
        assertThrows(ServiceException.class, () -> orderService.cancelOrder(DIGIT_TEN_STR, MESSAGE));
    }

    @Test
    void deleteOrderShouldReturnTrue() throws DaoException, ServiceException {
        given(orderDao.delete(anyLong())).willReturn(true);
        given(orderDao.findById(anyLong())).willReturn(Optional.of(createOrder()));
        assertTrue(orderService.deleteOrder(DIGIT_TEN_STR));
    }

    @Test
    void deleteOrderShouldReturnFalse() throws DaoException, ServiceException {
        given(orderDao.delete(anyLong())).willReturn(false);
        given(orderDao.findById(anyLong())).willReturn(Optional.of(createOrder()));
        assertFalse(orderService.deleteOrder(DIGIT_TEN_STR));
    }

    @Test
    void deleteOrderShouldThrowException() throws DaoException {
        given(orderDao.delete(anyLong())).willThrow(DaoException.class);
        given(orderDao.findById(anyLong())).willReturn(Optional.of(createOrder()));
        assertThrows(ServiceException.class, () -> orderService.deleteOrder(DIGIT_TEN_STR));
    }

    @Test
    void completeOrderShouldReturnTrue() throws DaoException, ServiceException {
        InputStream inputStream = new ByteArrayInputStream(new byte[64]);
        given(orderDao.completeOrder(any(Order.class), any(InputStream.class))).willReturn(true);
        given(orderDao.findById(anyLong())).willReturn(Optional.of(createOrder()));
        assertTrue(orderService.completeOrder(createReportData(), inputStream));
    }

    @Test
    void completeOrderShouldReturnFalse() throws DaoException, ServiceException {
        InputStream inputStream = new ByteArrayInputStream(new byte[64]);
        given(orderDao.completeOrder(any(Order.class), any(InputStream.class))).willReturn(false);
        given(orderDao.findById(anyLong())).willReturn(Optional.of(createOrder()));
        assertFalse(orderService.completeOrder(createReportData(), inputStream));
    }

    @Test
    void completeOrderShouldThrowsException() throws DaoException {
        InputStream inputStream = new ByteArrayInputStream(new byte[64]);
        given(orderDao.completeOrder(any(Order.class), any(InputStream.class))).willThrow(DaoException.class);
        given(orderDao.findById(anyLong())).willReturn(Optional.of(createOrder()));
        assertThrows(ServiceException.class, () -> orderService.completeOrder(createReportData(), inputStream));
    }

    @Test
    void findOrderReportByIdShouldExists() throws DaoException, ServiceException {
        OrderReport report = createOrderReport();
        given(orderDao.findOrderReportById(anyLong())).willReturn(Optional.of(report));
        assertThat(orderService.findOrderReportById(DIGIT_TEN_STR)).isPresent()
                .get()
                .isEqualTo(report);
    }

    @Test
    void findOrderReportByIdShouldNotExists() throws DaoException, ServiceException {
        given(orderDao.findOrderReportById(anyLong())).willReturn(Optional.empty());
        assertThat(orderService.findOrderReportById(DIGIT_TEN_STR)).isNotPresent();
    }

    @Test
    void findOrderReportByIdShouldThrowException() throws DaoException {
        given(orderDao.findOrderReportById(anyLong())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> orderService.findOrderReportById(DIGIT_TEN_STR));
    }

    @Test
    void countAllOrdersShouldReturnResult() throws DaoException, ServiceException {
        given(orderDao.countAllOrders()).willReturn(DIGIT_FIVE_INT);
        assertEquals(DIGIT_FIVE_INT, orderService.countAllOrders());
    }

    @Test
    void countAllOrdersShouldThrowException() throws DaoException {
        given(orderDao.countAllOrders()).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> orderService.countAllOrders());
    }

    @Test
    void countAllOrdersByStatusShouldReturnResult() throws DaoException, ServiceException {
        given(orderDao.countOrdersByStatus(any(OrderStatus.class))).willReturn(DIGIT_FIVE_INT);
        assertEquals(DIGIT_FIVE_INT, orderService.countOrdersByStatus(OrderStatus.ACTIVE));
    }

    @Test
    void countAllOrdersByStatusShouldThrowException() throws DaoException {
        given(orderDao.countOrdersByStatus(any(OrderStatus.class))).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> orderService.countOrdersByStatus(OrderStatus.ACTIVE));
    }

    @Test
    void countOrdersByUserIdShouldReturnResult() throws DaoException, ServiceException {
        given(orderDao.countOrdersByUserId(anyLong())).willReturn(DIGIT_FIVE_INT);
        assertEquals(DIGIT_FIVE_INT, orderService.countOrdersByUserId(DIGIT_ONE_LONG));
    }

    @Test
    void countOrdersByUserIdShouldThrowException() throws DaoException {
        given(orderDao.countOrdersByUserId(anyLong())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> orderService.countOrdersByUserId(DIGIT_ONE_LONG));
    }

    @Test
    void searchOrdersShouldReturnFilled() throws DaoException, ServiceException {
        List<Order> orders = List.of(createOrder());
        given(orderDao.searchOrders(anyString())).willReturn(orders);
        assertEquals(orders, orderService.searchOrders(DIGIT_TEN_STR));
    }

    @Test
    void searchOrdersShouldReturnEmpty() throws DaoException, ServiceException {
        given(orderDao.searchOrders(anyString())).willReturn(new ArrayList<>());
        assertThat(orderService.searchOrders(DIGIT_TEN_STR)).isEmpty();
    }

    @Test
    void searchOrdersShouldThrowException() throws DaoException {
        given(orderDao.searchOrders(anyString())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> orderService.searchOrders(DIGIT_TEN_STR));
    }

    @Test
    void calculateOrderPriceWithoutSale() {
        BigDecimal expectedPrice = new BigDecimal(5000);
        Car car = new Car();
        car.setRegularPrice(new BigDecimal(1000));
        assertEquals(expectedPrice, orderService.calculateOrderPrice(car, DIGIT_FIVE_INT));
    }

    @Test
    void calculateOrderPriceWithSale() {
        BigDecimal expectedPrice = new BigDecimal(2500);
        Car car = new Car();
        car.setRegularPrice(new BigDecimal(1000));
        car.setSalePrice(new BigDecimal(500));
        assertEquals(expectedPrice, orderService.calculateOrderPrice(car, DIGIT_FIVE_INT));
    }
}