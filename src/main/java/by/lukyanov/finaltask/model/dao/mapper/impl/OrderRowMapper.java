package by.lukyanov.finaltask.model.dao.mapper.impl;

import by.lukyanov.finaltask.entity.*;
import by.lukyanov.finaltask.model.dao.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

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
                    .id(rs.getLong("users_user_id"))
                    .name(rs.getString("name"))
                    .surname(rs.getString("surname"))
                    .status(UserStatus.valueOf(rs.getString("user_status")))
                    .build();
            Car car = new Car.CarBuilder()
                    .id(rs.getLong("cars_car_id"))
                    .brand(rs.getString("brand"))
                    .model(rs.getString("model"))
                    .active(rs.getBoolean("is_active"))
                    .build();
            Order order = new Order.OrderBuilder()
                    .id(rs.getLong("order_id"))
                    .beginDate(LocalDate.parse(rs.getString("begin_date")))
                    .endDate(LocalDate.parse(rs.getString("end_date")))
                    .orderStatus(OrderStatus.valueOf(rs.getString("order_status")))
                    .message(rs.getString("message"))
                    .user(user)
                    .car(car)
                    .price(rs.getBigDecimal("price"))
                    .build();
            String reportId = rs.getString("report_id");
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
