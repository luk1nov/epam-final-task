package by.lukyanov.finaltask.model.service;

import by.lukyanov.finaltask.entity.*;
import by.lukyanov.finaltask.exception.ServiceException;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {
    List<Order> findAllOrders(String page) throws ServiceException;

    List<Order> findAllOrdersByUserId(long userId) throws ServiceException;

    List<Order> findActiveOrderDatesByCarId(long carId) throws ServiceException;

    boolean addOrder(Car car, User user, String orderDateRange) throws ServiceException;

    List<Order> findOrdersByOrderStatus(OrderStatus status, String pageNumber) throws ServiceException;

    Optional<Order> findOrderById(String orderId) throws ServiceException;

    boolean updateOrderStatus(OrderStatus orderStatus, String orderId) throws ServiceException;

    boolean cancelOrder(String orderId, String message) throws ServiceException;

    boolean cancelOrder(String orderId) throws ServiceException;

    boolean deleteOrder(String orderId) throws ServiceException;

    boolean completeOrder(Map<String, String> reportData, InputStream reportPhoto) throws ServiceException;

    Optional<OrderReport> findOrderReportById(String reportId) throws ServiceException;

    int countAllOrders() throws ServiceException;

    int countOrdersByStatus(OrderStatus status) throws ServiceException;
}
