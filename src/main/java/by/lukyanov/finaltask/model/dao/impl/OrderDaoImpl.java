package by.lukyanov.finaltask.model.dao.impl;

import by.lukyanov.finaltask.entity.*;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.model.dao.mapper.impl.OrderReportMapper;
import by.lukyanov.finaltask.model.dao.mapper.impl.OrderRowMapper;
import by.lukyanov.finaltask.model.connection.ConnectionPool;
import by.lukyanov.finaltask.model.dao.OrderDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SQL_CREATE_ORDER = """
            INSERT INTO orders (begin_date, end_date, price, cars_car_id, users_user_id)
            VALUES (?,?,?,?,?);
            """;
    private static final String SQL_FIND_USER_BALANCE_BY_ID = "SELECT users.balance FROM users WHERE user_id = ?";
    private static final String SQL_UPDATE_USER_BALANCE_BY_ID = "UPDATE users SET balance = ? WHERE user_id = ?";
    private static final String SQL_FIND_ALL_ORDERS_BY_USER_ID = """
            SELECT o.order_id, o.begin_date, o.end_date, o.order_status, o.message, o.price, o.cars_car_id, c.brand, c.model
            FROM orders AS o
            JOIN cars AS c
            ON o.cars_car_id = c.car_id
            WHERE o.users_user_id = ?
            LIMIT ?
            OFFSET ?
            """;
    private static final String SQL_FIND_ALL_ORDERS = """
            SELECT o.order_id, o.begin_date, o.end_date, o.order_status, o.message, o.users_user_id, u.name, u.surname, u.user_status, o.cars_car_id, c.brand, c.model, c.is_active, o.price, r.report_id
            FROM orders as o
                    JOIN users as u
                         ON u.user_id = o.users_user_id
                    JOIN cars as c
                         ON c.car_id = o.cars_car_id
                    LEFT JOIN order_report as r
                              ON o.order_id = r.orders_order_id
            ORDER BY o.order_id
            LIMIT ?
            OFFSET ?
            """;
    private static final String SQL_FIND_ACTIVE_ORDER_DATES_BY_CAR_ID = """
            SELECT order_id, begin_date, end_date
            FROM orders
            WHERE cars_car_id = ?
            AND (order_status = 'ACTIVE' OR order_status = 'PROCESSING');
            """;
    private static final String SQL_FIND_ORDER_BY_ID = """
            SELECT o.order_id, o.begin_date, o.end_date, o.order_status, o.message, o.users_user_id, u.name, u.surname, u.user_status, o.cars_car_id, c.brand, c.model, c.is_active, o.price, r.report_id
            FROM orders as o
            JOIN users as u
            ON u.user_id = o.users_user_id
            JOIN cars as c
            ON c.car_id = o.cars_car_id
            LEFT JOIN order_report as r
            ON o.order_id = r.orders_order_id
            WHERE o.order_id = ?;
            """;
    private static final String SQL_FIND_ORDERS_BY_ORDER_STATUS = """
            SELECT o.order_id, o.begin_date, o.end_date, o.order_status, o.message, o.users_user_id, u.name, u.surname, u.user_status, o.cars_car_id, c.brand, c.model, c.is_active, o.price, r.report_id
            FROM orders as o
            JOIN users as u
            ON u.user_id = o.users_user_id
            JOIN cars as c
            ON c.car_id = o.cars_car_id
            LEFT JOIN order_report as r
            ON o.order_id = r.orders_order_id
            WHERE o.order_status = ?
            LIMIT ?
            OFFSET ?
            """;
    private static final String SQL_FIND_ORDERS_BETWEEN_DATES_BY_CAR_ID = """
            SELECT * from orders WHERE ? < end_date AND begin_date < ? AND cars_car_id = ? AND order_status = ?
            """;
    private static final String SQL_UPDATE_ORDER_STATUS_BY_ID = "UPDATE orders SET order_status = ? WHERE order_id = ?";
    private static final String SQL_DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE order_id = ?";
    private static final String SQL_UPDATE_ORDER_STATUS_AND_MESSAGE = "UPDATE orders SET order_status = ?, message = ? WHERE order_id = ?";
    private static final String SQL_ADD_ORDER_REPORT = "INSERT INTO order_report (report_photo, report_text, report_status, orders_order_id) values (?,?,?,?)";
    private static final String SQL_FIND_ORDER_REPORT_BY_ID = "SELECT report_photo, report_text, report_status FROM order_report WHERE report_id = ?";
    private static final String SQL_COUNT_ORDERS = "SELECT COUNT(order_id) from orders";
    private static final String SQL_COUNT_ORDERS_BY_STATUS = "SELECT COUNT(order_id) from orders WHERE order_status = ?";
    private static final String SQL_COUNT_ORDERS_BY_USER_ID = "SELECT COUNT(order_id) from orders WHERE users_user_id = ?";
    private static OrderDaoImpl instance;
    private final ConnectionPool pool = ConnectionPool.getInstance();


    private OrderDaoImpl() {
    }

    public static OrderDaoImpl getInstance(){
        if (instance == null){
            instance = new OrderDaoImpl();
        }
        return instance;
    }

    public boolean create(Order order) throws DaoException{
        boolean result = false;
        long userId = order.getUser().getId();
        try (Connection connection = pool.getConnection()){
            try (PreparedStatement findUserBalanceStmt = connection.prepareStatement(SQL_FIND_USER_BALANCE_BY_ID);
                 PreparedStatement updateBalanceStmt = connection.prepareStatement(SQL_UPDATE_USER_BALANCE_BY_ID);
                 PreparedStatement createOrderStmt = connection.prepareStatement(SQL_CREATE_ORDER)){
                connection.setAutoCommit(false);
                findUserBalanceStmt.setLong(1, userId);
                try (ResultSet rs = findUserBalanceStmt.executeQuery()){
                    if (rs.next()){
                        BigDecimal userBalance = rs.getBigDecimal(1);
                        userBalance = userBalance.subtract(order.getPrice());
                        updateBalanceStmt.setBigDecimal(1, userBalance);
                        updateBalanceStmt.setLong(2, userId);
                        updateBalanceStmt.executeUpdate();
                        createOrderStmt.setDate(1, Date.valueOf(order.getBeginDate()));
                        createOrderStmt.setDate(2, Date.valueOf(order.getEndDate()));
                        createOrderStmt.setBigDecimal(3, order.getPrice());
                        createOrderStmt.setLong(4, order.getCar().getId());
                        createOrderStmt.setLong(5, userId);
                        createOrderStmt.executeUpdate();
                        result = true;
                        connection.commit();
                        logger.info("order created");
                    } else {
                        logger.warn("User balance not found");
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                logger.error("Dao exception trying create order - rollback", e);
                connection.rollback();
                throw new SQLException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e){
            logger.error("Dao exception trying create order", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        boolean result;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ORDER_BY_ID)){
            statement.setLong(1, id);
            result = statement.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.error("Dao exception trying delete order", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<Order> findAll(int limit, int offset) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        OrderRowMapper rowMapper = OrderRowMapper.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_ORDERS)){
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            try(ResultSet rs = statement.executeQuery()){
                while (rs.next()){
                    Order order = rowMapper.mapRow(rs).get();
                    orderList.add(order);
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying find all orders", e);
            throw new DaoException(e);
        }
        return orderList;
    }

    @Override
    public boolean update(Order order) throws DaoException {
        boolean result;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ORDER_STATUS_BY_ID)){
            statement.setString(1, String.valueOf(order.getOrderStatus()));
            statement.setLong(2, order.getId());
            result = statement.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.error("Dao exception trying update order status", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<Order> findOrdersByUserId(long userId, int limit, int offset) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_ORDERS_BY_USER_ID)){
            statement.setLong(1, userId);
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            try (ResultSet rs = statement.executeQuery()){
                while (rs.next()){
                    Car car = new Car.CarBuilder()
                            .id(rs.getLong(7))
                            .brand(rs.getString(8))
                            .model(rs.getString(9))
                            .build();
                    Order order = new Order.OrderBuilder()
                            .id(rs.getLong(1))
                            .beginDate(LocalDate.parse(rs.getString(2)))
                            .endDate(LocalDate.parse(rs.getString(3)))
                            .orderStatus(OrderStatus.valueOf(rs.getString(4)))
                            .message(rs.getString(5))
                            .price(rs.getBigDecimal(6))
                            .car(car)
                            .build();
                    orderList.add(order);
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying find all user orders", e);
            throw new DaoException(e);
        }
        return orderList;
    }

    @Override
    public List<Order> findActiveOrderDatesByCarId(long carId) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ACTIVE_ORDER_DATES_BY_CAR_ID)){
            statement.setLong(1, carId);
            try (ResultSet rs = statement.executeQuery()){
                while (rs.next()){
                    Order order = new Order.OrderBuilder()
                            .id(rs.getLong(1))
                            .beginDate(LocalDate.parse(rs.getString(2)))
                            .endDate(LocalDate.parse(rs.getString(3)))
                            .build();
                    orderList.add(order);
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying find all active order dates", e);
            throw new DaoException(e);
        }
        return orderList;
    }

    @Override
    public boolean checkCarAvailabilityByDateRange(LocalDate beginDate, LocalDate endDate, long carId) throws DaoException {
        boolean result;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ORDERS_BETWEEN_DATES_BY_CAR_ID)){
            statement.setDate(1, Date.valueOf(beginDate));
            statement.setDate(2, Date.valueOf(endDate));
            statement.setLong(3, carId);
            statement.setString(4, String.valueOf(OrderStatus.ACTIVE));
            try (ResultSet rs = statement.executeQuery()){
                result = !rs.next();
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying check car availability by date range", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<Order> findOrdersByOrderStatus(OrderStatus orderStatus, int limit, int offset) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        OrderRowMapper orderRowMapper = OrderRowMapper.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ORDERS_BY_ORDER_STATUS)){
            statement.setString(1, String.valueOf(orderStatus));
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            try (ResultSet rs = statement.executeQuery()){
                while (rs.next()){
                    Order order = orderRowMapper.mapRow(rs).get();
                    orderList.add(order);
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying find orders by order status", e);
            throw new DaoException(e);
        }
        return orderList;
    }

    @Override
    public Optional<Order> findById(long id) throws DaoException {
        OrderRowMapper rowMapper = OrderRowMapper.getInstance();
        Optional<Order> optionalOrder = Optional.empty();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ORDER_BY_ID)){
            logger.debug("order id - " + id);
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    optionalOrder = rowMapper.mapRow(rs);
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying find order by id", e);
            throw new DaoException(e);
        }
        return optionalOrder;
    }

    @Override
    public boolean cancelOrder(Order order) throws DaoException {
        long userId = order.getUser().getId();
        boolean result = false;
        try (Connection connection = pool.getConnection()){
            try (PreparedStatement findUserBalanceStmt = connection.prepareStatement(SQL_FIND_USER_BALANCE_BY_ID);
                 PreparedStatement updateBalanceStmt = connection.prepareStatement(SQL_UPDATE_USER_BALANCE_BY_ID);
                 PreparedStatement updOrderStmt = connection.prepareStatement(SQL_UPDATE_ORDER_STATUS_AND_MESSAGE)){
                connection.setAutoCommit(false);
                findUserBalanceStmt.setLong(1, userId);
                try (ResultSet rs = findUserBalanceStmt.executeQuery()){
                    if (rs.next()){
                        BigDecimal userBalance = rs.getBigDecimal(1);
                        userBalance = userBalance.add(order.getPrice());
                        updateBalanceStmt.setBigDecimal(1, userBalance);
                        updateBalanceStmt.setLong(2, userId);
                        updateBalanceStmt.executeUpdate();
                        Optional<String> orderMessage = order.getMessage();
                        updOrderStmt.setString(1, String.valueOf(order.getOrderStatus()));
                        updOrderStmt.setString(2, orderMessage.orElse(null));
                        updOrderStmt.setLong(3, order.getId());
                        updOrderStmt.executeUpdate();
                        result = true;
                        connection.commit();
                    } else {
                        logger.warn("User balance not found");
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                logger.error("Dao exception trying decline order - rollback", e);
                connection.rollback();
                throw new SQLException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e){
            logger.error("Dao exception trying decline order", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public boolean completeOrder(Order order, InputStream reportPhoto) throws DaoException {
        boolean result;
        try (Connection connection = pool.getConnection()){
            try (PreparedStatement orderStmt = connection.prepareStatement(SQL_UPDATE_ORDER_STATUS_BY_ID);
                 PreparedStatement reportStmt = connection.prepareStatement(SQL_ADD_ORDER_REPORT)) {
                connection.setAutoCommit(false); // 1
                orderStmt.setString(1, String.valueOf(order.getOrderStatus()));
                orderStmt.setLong(2, order.getId());
                orderStmt.executeUpdate();
                reportStmt.setBlob(1, reportPhoto.available() != 0 ? reportPhoto : null);
                OrderReport orderReport = order.getReport().get();
                Optional<String> reportText = orderReport.getReportText();
                reportStmt.setString(2, reportText.orElse(null));
                reportStmt.setString(3, String.valueOf(orderReport.getReportStatus()));
                reportStmt.setLong(4, order.getId());
                reportStmt.executeUpdate();
                result = true;
                connection.commit();
                logger.info("successful query - commit");
            } catch (SQLException | IOException e) {
                logger.error("SQL exception trying complete order - rollback", e);
                connection.rollback();
                throw new SQLException(e);
            } finally {
                connection.setAutoCommit(true); // 4
            }
        } catch (SQLException e){
            logger.error("Dao exception trying complete order", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public Optional<OrderReport> findOrderReportById(long id) throws DaoException {
        Optional<OrderReport> optionalOrderReport = Optional.empty();
        OrderReportMapper mapper = OrderReportMapper.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ORDER_REPORT_BY_ID)){
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    optionalOrderReport = mapper.mapRow(rs);
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying find order report by id", e);
            throw new DaoException(e);
        }
        return optionalOrderReport;
    }

    @Override
    public int countAllOrders() throws DaoException {
        int orderCount = 0;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_COUNT_ORDERS);
             ResultSet rs = statement.executeQuery()){
            if(rs.next()){
                orderCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying count all orders", e);
            throw new DaoException(e);
        }
        return orderCount;
    }

    @Override
    public int countOrdersByStatus(OrderStatus status) throws DaoException {
        int orderCount = 0;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_COUNT_ORDERS_BY_STATUS)){
            statement.setString(1, String.valueOf(status));
            try (ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    orderCount = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying count orders by status", e);
            throw new DaoException(e);
        }
        return orderCount;
    }

    @Override
    public int countOrdersByUserId(long userId) throws DaoException {
        int orderCount = 0;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_COUNT_ORDERS_BY_USER_ID)){
            statement.setLong(1, userId);
            try (ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    orderCount = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying count orders by user id", e);
            throw new DaoException(e);
        }
        return orderCount;
    }
}
