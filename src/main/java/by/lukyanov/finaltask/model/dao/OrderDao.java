package by.lukyanov.finaltask.model.dao;

import by.lukyanov.finaltask.entity.Order;
import by.lukyanov.finaltask.entity.OrderReport;
import by.lukyanov.finaltask.entity.OrderStatus;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.exception.ServiceException;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * The order data access object interface
 */
public interface OrderDao extends BaseDao<Order>{
    /**
     * Creates new order.
     *
     * @param order Order object
     * @return true if order created, otherwise false
     * @throws DaoException if there is a problem with data access
     */
    boolean insert(Order order) throws DaoException;

    /**
     * Finds orders of specified user id.
     *
     * @param userId user id
     * @param limit requested row limit
     * @param offset from which row to get the result
     * @return List of requested user orders
     * @throws DaoException if there is a problem with data access
     */
    List<Order> findOrdersByUserId(long userId, int limit, int offset) throws DaoException;

    /**
     * Finds active orders of specified car id.
     *
     * @param carId car id
     * @return List of orders with requested car
     * @throws DaoException if there is a problem with data access
     */
    List<Order> findActiveOrderDatesByCarId(long carId) throws DaoException;

    /**
     * Checks car availability between specified dates.
     *
     * @param beginDate begin date
     * @param endDate end date
     * @param carId car id
     * @return true if car available between begin date and end date, otherwise false
     * @throws DaoException if there is a problem with data access
     */
    boolean checkCarAvailabilityByDateRange(LocalDate beginDate, LocalDate endDate, long carId) throws DaoException;

    /**
     * Finds orders with specified order status for requested page.
     *
     * @param orderStatus order status
     * @param limit requested row limit
     * @param offset from which row to get the result
     * @return List of order with requested order status
     * @throws DaoException if there is a problem with data access
     */
    List<Order> findOrdersByOrderStatus(OrderStatus orderStatus, int limit, int offset) throws DaoException;

    /**
     * Finds order with specified order id.
     *
     * @param id orderId
     * @return optional object of Order
     * @throws DaoException if there is a problem with data access
     */
    Optional<Order> findById(long id) throws DaoException;

    /**
     * Cancels order and returns money back.
     *
     * @param order object of order
     * @return true if order canceled, otherwise false
     * @throws DaoException if there is a problem with data access
     */
    boolean cancelOrder(Order order) throws DaoException;

    /**
     * Creates order report of specified order.
     *
     * @param order object of Order
     * @param reportPhoto InputStream of OrderReport photo
     * @return true if order completed, otherwise false
     * @throws DaoException if there is a problem with data access
     */
    boolean completeOrder(Order order, InputStream reportPhoto) throws DaoException;

    /**
     * Finds order report of specified report id.
     *
     * @param id report id
     * @return optional object of OrderReport
     * @throws DaoException if there is a problem with data access
     */
    Optional<OrderReport> findOrderReportById(long id) throws DaoException;

    /**
     * Counts all orders.
     *
     * @return number of all orders
     * @throws DaoException if there is a problem with data access
     */
    int countAllOrders() throws DaoException;

    /**
     * Counts all orders with specified order status.
     *
     * @param status OrderStatus object
     * @return number of orders of the requested order status
     * @throws DaoException if there is a problem with data access
     */
    int countOrdersByStatus(OrderStatus status) throws DaoException;

    /**
     * Counts all orders of specified user id.
     *
     * @param userId user id
     * @return number of orders of the requested user
     * @throws DaoException if there is a problem with data access
     */
    int countOrdersByUserId(long userId) throws DaoException;

    /**
     * Searches orders by user's name, surname, email, car's brand, model, vin code and order id.
     *
     * @param searchQuery search query
     * @return List of orders where user's name, surname, email,
     *         car's brand, model, vin code, order id contains search query
     * @throws DaoException if there is a problem with data access
     */
    List<Order> searchOrders(String searchQuery) throws DaoException;
}

