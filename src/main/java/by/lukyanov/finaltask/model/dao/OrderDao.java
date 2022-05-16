package by.lukyanov.finaltask.model.dao;

import by.lukyanov.finaltask.entity.Order;
import by.lukyanov.finaltask.entity.OrderStatus;
import by.lukyanov.finaltask.exception.DaoException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderDao extends BaseDao<Order>{
    List<Order> findOrdersByUserId(long userId) throws DaoException;

    List<Order> findActiveOrderDatesByCarId(long carId) throws DaoException;

    boolean checkCarAvailabilityByDateRange(LocalDate beginDate, LocalDate endDate, long carId) throws DaoException; //todo

    List<Order> findOrdersByOrderStatus(OrderStatus orderStatus) throws DaoException;

    boolean cancelOrder(Order order) throws DaoException;

    Optional<Order> findById(long id) throws DaoException;

    boolean updateOrderStatus(OrderStatus orderStatus, long id) throws DaoException;
    
    boolean declineOrder(Order order) throws DaoException;
}
