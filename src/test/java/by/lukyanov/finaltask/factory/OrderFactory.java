package by.lukyanov.finaltask.factory;

import by.lukyanov.finaltask.entity.Order;
import by.lukyanov.finaltask.entity.OrderReport;
import by.lukyanov.finaltask.entity.OrderReportStatus;
import by.lukyanov.finaltask.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

import static by.lukyanov.finaltask.factory.CarFactory.createCar;
import static by.lukyanov.finaltask.factory.UserFactory.createUser;

public class OrderFactory {
    public static final long id = 1L;
    public static final LocalDate beginDate = LocalDate.parse("2022-07-02");
    public static final LocalDate endDate = LocalDate.parse("2022-07-05");
    public static final OrderStatus status = OrderStatus.FINISHED;
    public static final String message = "message";
    public static final BigDecimal price = new BigDecimal(1500);

    public static Order createOrder(){
        return new Order.OrderBuilder()
                .id(id)
                .beginDate(beginDate)
                .endDate(endDate)
                .orderStatus(status)
                .message(message)
                .user(createUser())
                .car(createCar())
                .price(price)
                .build();
    }
}
