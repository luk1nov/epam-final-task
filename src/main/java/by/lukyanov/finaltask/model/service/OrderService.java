package by.lukyanov.finaltask.model.service;

import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.entity.Order;
import by.lukyanov.finaltask.entity.OrderStatus;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAllOrdersByUserId(long userId) throws ServiceException;

    List<Order> findActiveOrderDatesByCarId(long carId) throws ServiceException;

    boolean addOrder(Car car, User user, String orderDateRange) throws ServiceException;

    List<Order> findOrdersByOrderStatus(OrderStatus status) throws ServiceException;

    boolean cancelOrder(Order order) throws ServiceException;

    Optional<Order> findOrderById(String orderId) throws ServiceException;

    boolean updateOrderStatus(OrderStatus orderStatus, String orderId) throws ServiceException;

    boolean declineOrder(String orderId, String message) throws ServiceException;
}
