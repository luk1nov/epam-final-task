package by.lukyanov.finaltask.model.service;

import by.lukyanov.finaltask.entity.*;
import by.lukyanov.finaltask.exception.ServiceException;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The order service interface.
 */
public interface OrderService {
    /**
     * Finds all orders for requested page.
     *
     * @param page requested number of page
     * @param postsPerPage number of posts per page
     * @return List of orders for requested page
     * @throws ServiceException if there is a problem with order service
     */
    List<Order> findAllOrders(String page, int postsPerPage) throws ServiceException;

    /**
     * Finds all user orders for requested page.
     *
     * @param userId user id
     * @param pageNumber requested number of page
     * @param postsPerPage number of posts per page
     * @return List of user's orders for requested page
     * @throws ServiceException if there is a problem with order service
     */
    List<Order> findAllOrdersByUserId(long userId, String pageNumber, int postsPerPage) throws ServiceException;

    /**
     * Finds active and processing orders for specified car between two dates.
     *
     * @param carId car id
     * @return List of active and processing orders for specified car
     * @throws ServiceException if there is a problem with order service
     */
    List<Order> findActiveOrderDatesByCarId(String carId) throws ServiceException;

    /**
     * Creates new order.
     *
     * @param order order object
     * @param orderDateRange string with date range
     * @return true if date range is valid and order created, otherwise false
     * @throws ServiceException if there is a problem with order service
     */
    boolean addOrder(Order order, String orderDateRange) throws ServiceException;

    /**
     * Finds orders with specified status.
     *
     * @param status order status object
     * @param pageNumber requested number of page
     * @param postsPerPage number of posts per page
     * @return List of orders with specified order status
     * @throws ServiceException if there is a problem with order service
     */
    List<Order> findOrdersByOrderStatus(OrderStatus status, String pageNumber, int postsPerPage) throws ServiceException;

    /**
     * Finds order with specified id.
     *
     * @param orderId order id
     * @return optional object of order
     * @throws ServiceException if there is a problem with order service
     */
    Optional<Order> findOrderById(String orderId) throws ServiceException;

    /**
     * Updates order status with specified id.
     *
     * @param orderStatus order status object
     * @param orderId order id
     * @return true if order's status updated, otherwise false
     * @throws ServiceException if there is a problem with order service
     */
    boolean updateOrderStatus(OrderStatus orderStatus, String orderId) throws ServiceException;

    /**
     * Cancels order as admin (manager) with specified reason.
     *
     * @param orderId order id
     * @param message reason for cancellation
     * @return true if order canceled and money returns, otherwise false
     * @throws ServiceException if there is a problem with order service
     */
    boolean cancelOrder(String orderId, String message) throws ServiceException;

    /**
     * Cancels order as client.
     *
     * @param orderId order id
     * @return true if order canceled and money returns, otherwise false
     * @throws ServiceException if there is a problem with order service
     */
    boolean cancelOrder(String orderId) throws ServiceException;

    /**
     * Deletes order if its cancelled or finished.
     *
     * @param orderId order id
     * @return true if order deleted, otherwise false
     * @throws ServiceException if there is a problem with order service
     */
    boolean deleteOrder(String orderId) throws ServiceException;

    /**
     * Creates order report and complete order.
     *
     * @param reportData Map of report data
     * @param reportPhoto InputStream of report photo
     * @return true if order completed, otherwise false
     * @throws ServiceException if there is a problem with order service
     */
    boolean completeOrder(Map<String, String> reportData, InputStream reportPhoto) throws ServiceException;

    /**
     * Finds order report with specified report id.
     *
     * @param reportId report id
     * @return optional object of order report
     * @throws ServiceException if there is a problem with order service
     */
    Optional<OrderReport> findOrderReportById(String reportId) throws ServiceException;

    /**
     * Counts all orders.
     *
     * @return number of all orders
     * @throws ServiceException if there is a problem with order service
     */
    int countAllOrders() throws ServiceException;

    /**
     * Counts all orders with specified order status.
     *
     * @param status order status object
     * @return number of orders with specified status
     * @throws ServiceException if there is a problem with order service
     */
    int countOrdersByStatus(OrderStatus status) throws ServiceException;

    /**
     * Counts all orders of specified user.
     *
     * @param userId user id
     * @return number of specified user orders
     * @throws ServiceException if there is a problem with order service
     */
    int countOrdersByUserId(long userId) throws ServiceException;

    /**
     * Searches orders by user's name, surname, email,
     * car's brand, model, vin code and order id.
     *
     * @param searchQuery search query
     * @return List of orders where user's name, surname, email,
     *         car's brand, model, vin code or order id contains search query
     * @throws ServiceException if there is a problem with order service
     */
    List<Order> searchOrders(String searchQuery) throws ServiceException;

    /**
     * Calculates order price based on the number of days
     *
     * @param car Car object
     * @param orderDays number of days
     * @return order price
     */
    BigDecimal calculateOrderPrice(Car car, int orderDays);
}
