package by.lukyanov.finaltask.model.dao.mapper.impl;

import by.lukyanov.finaltask.entity.*;
import by.lukyanov.finaltask.model.dao.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import static by.lukyanov.finaltask.model.dao.ColumnName.*;

public class OrderRowMapper implements RowMapper<Order> {
    private static final Logger logger = LogManager.getLogger();
    private static OrderRowMapper instance;

    private OrderRowMapper() {
    }

    public static OrderRowMapper getInstance(){
        if(instance == null){
            instance = new OrderRowMapper();
        }
        return instance;
    }

    @Override
    public Optional<Order> mapRow(ResultSet rs){
        Optional<Order> optionalOrder;
        try {
            User user = new User.UserBuilder()
                    .id(rs.getLong(USER_ID))
                    .name(rs.getString(USER_NAME))
                    .surname(rs.getString(USER_SURNAME))
                    .status(UserStatus.valueOf(rs.getString(USER_STATUS)))
                    .build();
            Car car = new Car.CarBuilder()
                    .id(rs.getLong(CAR_ID))
                    .brand(rs.getString(CAR_BRAND))
                    .model(rs.getString(CAR_MODEL))
                    .active(rs.getBoolean(CAR_IS_ACTIVE))
                    .build();
            Order order = new Order.OrderBuilder()
                    .id(rs.getLong(ORDER_ID))
                    .beginDate(LocalDate.parse(rs.getString(ORDER_BEGIN_DATE)))
                    .endDate(LocalDate.parse(rs.getString(ORDER_END_DATE)))
                    .orderStatus(OrderStatus.valueOf(rs.getString(ORDER_STATUS)))
                    .message(rs.getString(ORDER_MESSAGE))
                    .user(user)
                    .car(car)
                    .price(rs.getBigDecimal(ORDER_PRICE))
                    .build();
            String reportId = rs.getString(REPORT_ID);
            if (reportId != null){
                order.setReport(new OrderReport(Long.parseLong(reportId)));
            }
            optionalOrder = Optional.of(order);
        } catch (SQLException e){
            logger.error("Can not read resultset", e);
            optionalOrder = Optional.empty();
        }
        return optionalOrder;
    }
}
