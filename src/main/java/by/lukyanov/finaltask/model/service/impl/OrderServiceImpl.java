package by.lukyanov.finaltask.model.service.impl;

import by.lukyanov.finaltask.entity.*;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.dao.impl.OrderDaoImpl;
import by.lukyanov.finaltask.model.service.OrderService;
import by.lukyanov.finaltask.util.DateRangeParser;
import by.lukyanov.finaltask.util.ResultCounter;
import by.lukyanov.finaltask.validation.impl.ValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.lukyanov.finaltask.command.ParameterAttributeName.*;
import static by.lukyanov.finaltask.util.DateRangeParser.BEGIN_DATE_INDEX;
import static by.lukyanov.finaltask.util.DateRangeParser.END_DATE_INDEX;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderDaoImpl orderDaoImpl = OrderDaoImpl.getInstance();
    private static final ValidatorImpl validator = ValidatorImpl.getInstance();
    private static final int DEFAULT_RESULT_PAGE = 1;

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
}
