package by.lukyanov.finaltask.model.dao;

import by.lukyanov.finaltask.entity.Order;
import by.lukyanov.finaltask.entity.OrderReport;
import by.lukyanov.finaltask.entity.OrderStatus;
import by.lukyanov.finaltask.exception.DaoException;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderDao extends BaseDao<Order>{
    List<Order> findOrdersByUserId(long userId, int limit, int offset) throws DaoException;

    List<Order> findActiveOrderDatesByCarId(long carId) throws DaoException;

    boolean checkCarAvailabilityByDateRange(LocalDate beginDate, LocalDate endDate, long carId) throws DaoException;

    List<Order> findOrdersByOrderStatus(OrderStatus orderStatus, int limit, int offset) throws DaoException;

    Optional<Order> findById(long id) throws DaoException;

    boolean cancelOrder(Order order) throws DaoException;

    boolean completeOrder(Order order, InputStream reportPhoto) throws DaoException;

    Optional<OrderReport> findOrderReportById(long id) throws DaoException;

    int countAllOrders() throws DaoException;

    int countOrdersByStatus(OrderStatus status) throws DaoException;

    int countOrdersByUserId(long userId) throws DaoException;
}

