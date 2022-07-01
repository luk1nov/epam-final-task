package by.lukyanov.finaltask.model.service.impl;

import by.lukyanov.finaltask.entity.*;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.dao.impl.OrderDaoImpl;
import by.lukyanov.finaltask.model.service.OrderService;
import by.lukyanov.finaltask.util.DateRangeCounter;
import by.lukyanov.finaltask.util.ResultCounter;
import by.lukyanov.finaltask.validation.impl.ValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger();
    private static final ValidatorImpl validator = ValidatorImpl.getInstance();
    private static final int DEFAULT_RESULT_PAGE = 1;
    private static final int MIN_RENT_DAYS = 1;
    private static final OrderServiceImpl instance = new OrderServiceImpl();
    private OrderDaoImpl orderDaoImpl;

    private OrderServiceImpl() {
        orderDaoImpl = OrderDaoImpl.getInstance();
    }

    public static OrderServiceImpl getInstance(){
        return instance;
    }

    @Override
    public List<Order> findAllOrders(String pageNumber, int postsPerPage) throws ServiceException {
        List<Order> orderList;
        try {
            int orderPage = validator.isValidNumber(pageNumber) ? Integer.parseInt(pageNumber) : DEFAULT_RESULT_PAGE;
            ResultCounter counter = new ResultCounter(orderPage, postsPerPage);
            orderList = orderDaoImpl.findAll(postsPerPage, counter.offset());
        } catch (DaoException e) {
            logger.error("Service exception trying find all orders", e);
            throw new ServiceException(e);
        }
        return orderList;
    }

    @Override
    public List<Order> findAllOrdersByUserId(long userId, String pageNumber, int postsPerPage) throws ServiceException {
        List<Order> orderList;
        try {
            int ordersPage = validator.isValidNumber(pageNumber) ? Integer.parseInt(pageNumber) : DEFAULT_RESULT_PAGE;
            ResultCounter counter = new ResultCounter(ordersPage, postsPerPage);
            orderList = orderDaoImpl.findOrdersByUserId(userId, postsPerPage, counter.offset());
        } catch (DaoException e) {
            logger.error("Service exception trying find all user orders", e);
            throw new ServiceException(e);
        }
        return orderList;
    }

    @Override
    public List<Order> findActiveOrderDatesByCarId(String carId) throws ServiceException {
        List<Order> orderList = new ArrayList<>();
        try {
            if(validator.isValidId(carId)){
                orderList = orderDaoImpl.findActiveOrderDatesByCarId(Long.parseLong(carId));
            }
        } catch (DaoException e) {
            logger.error("Service exception trying find all active order dates", e);
            throw new ServiceException(e);
        }
        return orderList;
    }

    @Override
    public boolean addOrder(Order order, String orderDateRange) throws ServiceException {
        boolean result = false;
        Car car = order.getCar();
        User user = order.getUser();
        if(validator.isValidDateRange(orderDateRange)){
            try {
                DateRangeCounter counter = new DateRangeCounter(orderDateRange);
                int orderDays = counter.countDays();
                BigDecimal orderPrice = calculateOrderPrice(car, orderDays);
                if (orderDays >= MIN_RENT_DAYS && orderDaoImpl.checkCarAvailabilityByDateRange(counter.getBeginDate(), counter.getEndDate(), car.getId())){
                    order = new Order.OrderBuilder()
                            .beginDate(counter.getBeginDate())
                            .endDate(counter.getEndDate())
                            .car(car)
                            .user(user)
                            .price(orderPrice)
                            .build();
                    result = orderDaoImpl.create(order);
                } else {
                    logger.warn("invalid date range or car unavailable");
                }
            } catch (DaoException e) {
                logger.error("Service exception trying create order", e);
                throw new ServiceException(e);
            }
        } else {
            logger.warn("invalid data provided");
        }
        return result;
    }

    @Override
    public List<Order> findOrdersByOrderStatus(OrderStatus status, String pageNumber, int postsPerPage) throws ServiceException {
        List<Order> orderList;
        try {
            int orderPage = validator.isValidNumber(pageNumber) ? Integer.parseInt(pageNumber) : DEFAULT_RESULT_PAGE;
            ResultCounter counter = new ResultCounter(orderPage, postsPerPage);
            orderList = orderDaoImpl.findOrdersByOrderStatus(status, postsPerPage, counter.offset());
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
                Order order = new Order.OrderBuilder()
                        .id(Long.valueOf(orderId))
                        .orderStatus(orderStatus)
                        .build();
                result = orderDaoImpl.update(order);
            }
        } catch (DaoException e) {
            logger.error("Service exception trying update order status", e);
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean cancelOrder(String orderId, String message) throws ServiceException {
        boolean result = false;
        try {
            if(validator.isValidMessage(message) && validator.isValidId(orderId)){
                Optional<Order> optionalOrder = orderDaoImpl.findById(Long.parseLong(orderId));
                if(optionalOrder.isPresent()){
                    Order order = optionalOrder.get();
                    order.setOrderStatus(OrderStatus.CANCELED);
                    order.setMessage(message);
                    result = orderDaoImpl.cancelOrder(order);
                }
            }
        } catch (DaoException e) {
            logger.error("Service exception trying decline order", e);
            throw new ServiceException(e);
        }
        return result;
    }


    @Override
    public boolean cancelOrder(String orderId) throws ServiceException {
        boolean result = false;
        try {
            if(validator.isValidId(orderId)){
                Optional<Order> optionalOrder = orderDaoImpl.findById(Long.parseLong(orderId));
                if(optionalOrder.isPresent()){
                    Order order = optionalOrder.get();
                    order.setOrderStatus(OrderStatus.CANCELED);
                    result = orderDaoImpl.cancelOrder(order);
                }
            }
        } catch (DaoException e) {
            logger.error("Service exception trying decline order", e);
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean deleteOrder(String orderId) throws ServiceException {
        boolean result = false;
        try {
            if(validator.isValidId(orderId)){
                Optional<Order> optionalOrder = orderDaoImpl.findById(Long.parseLong(orderId));
                if(optionalOrder.isPresent()){
                    Order order = optionalOrder.get();
                    OrderStatus orderStatus = order.getOrderStatus();
                    if(orderStatus == OrderStatus.CANCELED || orderStatus == OrderStatus.FINISHED){
                        result = orderDaoImpl.delete(Long.parseLong(orderId));
                    }
                }
            }
        } catch (DaoException e) {
            logger.error("Service exception trying delete order", e);
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean completeOrder(Map<String, String> reportData, InputStream reportPhoto) throws ServiceException {
        String reportStatus = reportData.get(ORDER_REPORT_STATUS);
        String reportText = reportData.get(ORDER_REPORT_TEXT);
        String orderId = reportData.get(ORDER_ID);
        boolean result = false;
        if (validator.isValidId(orderId) && validator.isValidMessage(reportText)){
            try {
                Optional<Order> optionalOrder = orderDaoImpl.findById(Long.parseLong(orderId));
                if (optionalOrder.isPresent()){
                    Order order = createReport(reportText, reportStatus, orderId);
                    result = orderDaoImpl.completeOrder(order, reportPhoto);
                }
            } catch (DaoException | IllegalArgumentException e) {
                logger.error("Service exception trying complete order", e);
                throw new ServiceException(e);
            }
        }
        return result;
    }

    @Override
    public Optional<OrderReport> findOrderReportById(String reportId) throws ServiceException {
        Optional<OrderReport> optionalOrderReport = Optional.empty();
        try {
            if (validator.isValidId(reportId)){
                optionalOrderReport = orderDaoImpl.findOrderReportById(Long.parseLong(reportId));
            }
        } catch (DaoException e) {
            logger.error("Service exception trying find order report by id", e);
            throw new ServiceException(e);
        }
        return optionalOrderReport;
    }

    @Override
    public int countAllOrders() throws ServiceException {
        try {
            return orderDaoImpl.countAllOrders();
        } catch (DaoException e) {
            logger.error("Service exception trying count all orders", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public int countOrdersByStatus(OrderStatus status) throws ServiceException {
        try {
            return orderDaoImpl.countOrdersByStatus(status);
        } catch (DaoException e) {
            logger.error("Service exception trying count orders by status", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public int countOrdersByUserId(long userId) throws ServiceException {
        try {
            return orderDaoImpl.countOrdersByUserId(userId);
        } catch (DaoException e) {
            logger.error("Service exception trying count orders by status", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> searchOrders(String searchQuery) throws ServiceException {
        List<Order> orders = new ArrayList<>();
        try {
            if (validator.isValidSearchPattern(searchQuery)){
                orders = orderDaoImpl.searchOrders(searchQuery.strip());
            }
        } catch (DaoException e) {
            logger.error("Service exception trying search users", e);
            throw new ServiceException(e);
        }
        return orders;
    }

    private Order createReport(String text, String status, String orderId) throws IllegalArgumentException{
        OrderReport report = new OrderReport();
        if (!text.isBlank()){
            report.setReportText(text);
        }
        report.setReportStatus(OrderReportStatus.valueOf(status.toUpperCase()));
        return new Order.OrderBuilder()
                .id(Long.valueOf(orderId))
                .orderStatus(OrderStatus.FINISHED)
                .report(report)
                .build();
    }

    public BigDecimal calculateOrderPrice(Car car, int orderDays){
        BigDecimal orderPrice = car.getSalePrice().isPresent() ? car.getSalePrice().get() : car.getRegularPrice();
        return orderPrice.multiply(BigDecimal.valueOf(orderDays));
    }
}
