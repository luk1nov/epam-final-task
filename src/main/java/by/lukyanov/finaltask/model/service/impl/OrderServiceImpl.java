package by.lukyanov.finaltask.model.service.impl;

import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.entity.Order;
import by.lukyanov.finaltask.entity.OrderStatus;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.dao.impl.OrderDaoImpl;
import by.lukyanov.finaltask.model.service.OrderService;
import by.lukyanov.finaltask.util.DateRangeParser;
import by.lukyanov.finaltask.validation.impl.ValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static by.lukyanov.finaltask.util.DateRangeParser.BEGIN_DATE_INDEX;
import static by.lukyanov.finaltask.util.DateRangeParser.END_DATE_INDEX;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderDaoImpl orderDaoImpl = OrderDaoImpl.getInstance();
    private static final ValidatorImpl validator = ValidatorImpl.getInstance();

    @Override
    public List<Order> findAllOrdersByUserId(long userId) throws ServiceException {
        List<Order> orderList;
        try {
            orderList = orderDaoImpl.findOrdersByUserId(userId);
        } catch (DaoException e) {
            logger.error("Service exception trying find all user orders", e);
            throw new ServiceException(e);
        }
        return orderList;
    }

    @Override
    public List<Order> findActiveOrderDatesByCarId(long carId) throws ServiceException {
        List<Order> orderList;
        try {
            orderList = orderDaoImpl.findActiveOrderDatesByCarId(carId);
        } catch (DaoException e) {
            logger.error("Service exception trying find all active order dates", e);
            throw new ServiceException(e);
        }
        return orderList;
    }

    @Override
    public boolean addOrder(Car car, User user, String orderDateRange) throws ServiceException {
        boolean result = false;
        if(validator.isValidDateRange(orderDateRange)){
            try {
                List<LocalDate> dateRange = DateRangeParser.parse(orderDateRange);
                LocalDate beginDate = dateRange.get(BEGIN_DATE_INDEX);
                LocalDate endDate = dateRange.size() > 1 ? dateRange.get(END_DATE_INDEX) : dateRange.get(BEGIN_DATE_INDEX);
                int orderDays = DateRangeParser.countDays(beginDate, endDate);
                BigDecimal orderPrice = car.getSalePrice().isPresent() ? car.getSalePrice().get() : car.getRegularPrice();
                orderPrice = orderPrice.multiply(BigDecimal.valueOf(orderDays));
                logger.info("orderdays " + (orderDays >= 1));
                logger.info("compare " + (user.getBalance().compareTo(orderPrice) > -1));
                logger.info("available " + orderDaoImpl.checkCarAvailabilityByDateRange(beginDate, endDate, car.getId()));
                if (orderDays >= 1 && user.getBalance().compareTo(orderPrice) > -1 &&
                        orderDaoImpl.checkCarAvailabilityByDateRange(beginDate, endDate, car.getId())){
                    Order order = new Order.OrderBuilder()
                            .beginDate(beginDate)
                            .endDate(endDate)
                            .car(car)
                            .user(user)
                            .price(orderPrice)
                            .build();
                    logger.debug("before create order");
                    result = orderDaoImpl.create(order);
                } else {
                    logger.warn("invalid date range or car unavailable");
                }
            } catch (DaoException e) {
                logger.error("Service exception trying create order");
            }
        } else {
            logger.warn("invalid data provided");
        }
        return result;
    }

    @Override
    public List<Order> findOrdersByOrderStatus(OrderStatus status) throws ServiceException {
        List<Order> orderList;
        try {
            orderList = orderDaoImpl.findOrdersByOrderStatus(status);
        } catch (DaoException e) {
            logger.error("Service exception trying find processing orders", e);
            throw new ServiceException(e);
        }
        return orderList;
    }

    @Override
    public Optional<Order> findOrderById(String orderId) throws ServiceException {
        Optional<Order> order = Optional.empty();
        try {
            if (validator.isValidId(orderId)) {
                order = orderDaoImpl.findById(Long.parseLong(orderId));
            }
        } catch (DaoException e) {
            logger.error("Service exception trying find order by id", e);
            throw new ServiceException(e);
        }
        return order;
    }

    @Override
    public boolean updateOrderStatus(OrderStatus orderStatus, String orderId) throws ServiceException {
        boolean result = false;
        try {
            if(validator.isValidId(orderId)){
                result = orderDaoImpl.updateOrderStatus(orderStatus, Long.parseLong(orderId));
            }
        } catch (DaoException e) {
            logger.error("Service exception trying update order status", e);
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean declineOrder(String orderId, String message) throws ServiceException {
        boolean result = false;
        try {
            if(validator.isValidDeclineMessage(message) && validator.isValidId(orderId)){
                Order order = new Order.OrderBuilder()
                        .id(Long.valueOf(orderId))
                        .message(message)
                        .orderStatus(OrderStatus.REJECTED)
                        .build();
                result = orderDaoImpl.declineOrder(order);
            }
        } catch (DaoException e) {
            logger.error("Service exception trying decline order", e);
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean cancelOrder(Order order) throws ServiceException {
        boolean result;
        try {
            order.setOrderStatus(OrderStatus.FINISHED);
            result = orderDaoImpl.cancelOrder(order);
        } catch (DaoException e) {
            logger.error("Service exception trying cancel order", e);
            throw new ServiceException(e);
        }
        return result;
    }
}
