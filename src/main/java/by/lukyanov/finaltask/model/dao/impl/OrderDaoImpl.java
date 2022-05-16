package by.lukyanov.finaltask.model.dao.impl;

import by.lukyanov.finaltask.entity.*;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.model.connection.ConnectionPool;
import by.lukyanov.finaltask.model.dao.OrderDao;
import com.oracle.wls.shaded.org.apache.xpath.operations.Or;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String CREATE_ORDER = """
            INSERT INTO orders (begin_date, end_date, price, cars_car_id, users_user_id) 
            VALUES (?,?,?,?,?);
            """;
    private static final String FIND_ALL_ORDERS_BY_USER_ID = """
            SELECT o.order_id, o.begin_date, o.end_date, o.order_status, o.message, o.price, o.cars_car_id, c.brand, c.model
            FROM orders AS o
            JOIN cars AS c
            ON o.cars_car_id = c.car_id
            WHERE o.users_user_id = ?;
            """;
    private static final String FIND_ACTIVE_ORDER_DATES_BY_CAR_ID = """
            SELECT order_id, begin_date, end_date
            FROM orders
            WHERE cars_car_id = ?
            AND (order_status = 'ACTIVE' OR order_status = 'PROCESSING');
            """;
    private static final String FIND_BY_ID = """
            SELECT order_id, begin_date, end_date, order_status, message, users_user_id, cars_car_id, price
            FROM orders
            WHERE order_id = ?;
            """;
    private static final String FIND_ORDERS_BY_ORDER_STATUS = """
            SELECT o.order_id, o.begin_date, o.end_date, o.order_status, o.users_user_id, u.name, u.surname, u.user_status, o.cars_car_id, c.brand, c.model, c.is_active, o.price
            FROM orders as o
            JOIN users as u
            ON u.user_id = o.users_user_id
            JOIN cars as c
            ON c.car_id = o.cars_car_id
            WHERE o.order_status = ?;
            """;
    private static final String FIND_ORDERS_BETWEEN_DATES_BY_CAR_ID = """
            SELECT * from orders WHERE ? < end_date AND begin_date < ? AND cars_car_id = ? AND order_status = ?
            """;
    private static final String UPDATE_ORDER_STATUS = "UPDATE orders SET order_status = ? WHERE order_id = ?";
    private static final String UPDATE_ORDER_STATUS_AND_MESSAGE = "UPDATE orders SET order_status = ?, message = ? WHERE order_id = ?";
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
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean result;
        try (Connection connection = pool.getConnection()){
            try (PreparedStatement createOrderStmt = connection.prepareStatement(CREATE_ORDER)){
                connection.setAutoCommit(false);
                userDao.updateBalance(order.getUser().getId(), order.getPrice(), true); //todo exception double transaction
                createOrderStmt.setDate(1, Date.valueOf(order.getBeginDate()));
                createOrderStmt.setDate(2, Date.valueOf(order.getEndDate()));
                createOrderStmt.setBigDecimal(3, order.getPrice());
                createOrderStmt.setLong(4, order.getCar().getId());
                createOrderStmt.setLong(5, order.getUser().getId());
                createOrderStmt.executeUpdate();
                logger.info("before upd balance");
                result = true;
                connection.commit();
                logger.info("order created");
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
        return false;
    }

    @Override
    public List<Order> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean update(Order order) throws DaoException {
        return false;
    }

    @Override
    public List<Order> findOrdersByUserId(long userId) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ORDERS_BY_USER_ID)){
            statement.setLong(1, userId);
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
             PreparedStatement statement = connection.prepareStatement(FIND_ACTIVE_ORDER_DATES_BY_CAR_ID)){
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
        boolean result= false;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ORDERS_BETWEEN_DATES_BY_CAR_ID)){
            statement.setDate(1, Date.valueOf(beginDate));
            statement.setDate(2, Date.valueOf(endDate));
            statement.setLong(3, carId);
            statement.setString(4, String.valueOf(OrderStatus.ACTIVE));
            try (ResultSet rs = statement.executeQuery()){
                if (!rs.next()){
                    result = true;
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying check car availability by date range", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<Order> findOrdersByOrderStatus(OrderStatus orderStatus) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ORDERS_BY_ORDER_STATUS)){
            statement.setString(1, String.valueOf(orderStatus));
            try (ResultSet rs = statement.executeQuery()){
                while (rs.next()){
                    User user = new User.UserBuilder()
                            .id(rs.getLong(5))
                            .name(rs.getString(6))
                            .surname(rs.getString(7))
                            .status(UserStatus.valueOf(rs.getString(8)))
                            .build();
                    Car car = new Car.CarBuilder()
                            .id(rs.getLong(9))
                            .brand(rs.getString(10))
                            .model(rs.getString(11))
                            .active(rs.getBoolean(12))
                            .build();
                    Order order = new Order.OrderBuilder()
                            .id(rs.getLong(1))
                            .beginDate(LocalDate.parse(rs.getString(2)))
                            .endDate(LocalDate.parse(rs.getString(3)))
                            .orderStatus(OrderStatus.valueOf(rs.getString(4)))
                            .car(car)
                            .user(user)
                            .price(rs.getBigDecimal(13))
                            .build();
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
    public boolean cancelOrder(Order order) throws DaoException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean result;
        try (Connection connection = pool.getConnection()){
            try (PreparedStatement updateStatusStmt = connection.prepareStatement(UPDATE_ORDER_STATUS)){
                connection.setAutoCommit(false);
                logger.debug("order price " + order.getPrice());
                userDao.updateBalance(order.getUser().getId(), order.getPrice(), false);
                updateStatusStmt.setString(1, String.valueOf(order.getOrderStatus()));
                updateStatusStmt.setLong(2, order.getId());
                updateStatusStmt.executeUpdate();
                result = true;
                connection.commit();
            } catch (SQLException e) {
                logger.error("Dao exception trying cancel order - rollback", e);
                connection.rollback();
                throw new SQLException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e){
            logger.error("Dao exception trying cancel order", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public Optional<Order> findById(long id) throws DaoException {
        Optional<Order> optionalOrder = Optional.empty();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)){
            logger.debug("order id - " + id);
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    User user = new User(rs.getLong(6));
                    Car car = new Car(rs.getLong(7));
                    Order order = new Order.OrderBuilder()
                            .id(rs.getLong(1))
                            .beginDate(LocalDate.parse(rs.getString(2)))
                            .endDate(LocalDate.parse(rs.getString(3)))
                            .orderStatus(OrderStatus.valueOf(rs.getString(4)))
                            .message(rs.getString(5))
                            .user(user)
                            .car(car)
                            .price(rs.getBigDecimal(8))
                            .build();
                    optionalOrder = Optional.of(order);
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying find order by id", e);
            throw new DaoException(e);
        }
        return optionalOrder;
    }

    @Override
    public boolean updateOrderStatus(OrderStatus orderStatus, long id) throws DaoException {
        boolean result = false;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER_STATUS)){
            statement.setString(1, String.valueOf(orderStatus));
            statement.setLong(2, id);
            if(statement.executeUpdate() != 0){
                result = true;
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying update order status", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public boolean declineOrder(Order order) throws DaoException {
        boolean result = false;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER_STATUS_AND_MESSAGE)){
            statement.setString(1, String.valueOf(order.getOrderStatus()));
            statement.setString(2, order.getMessage().isBlank() ? null : order.getMessage());
            statement.setLong(3, order.getId());
            if(statement.executeUpdate() != 0){
                result = true;
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying decline order", e);
            throw new DaoException(e);
        }
        return result;
    }
}
