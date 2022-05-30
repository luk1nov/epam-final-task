package by.lukyanov.finaltask.entity;

import com.oracle.wls.shaded.org.apache.xpath.operations.Or;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class Order extends AbstractEntity {
    private LocalDate beginDate;
    private LocalDate endDate;
    private OrderStatus orderStatus;
    private String message;
    private BigDecimal price;
    private User user;
    private Car car;
    private OrderReport report;

    public Order() {
    }

    public Order(long id) {
        super(id);
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Optional<String> getMessage() {
        return Optional.ofNullable(message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Optional<OrderReport> getReport() {
        return Optional.ofNullable(report);
    }

    public void setReport(OrderReport report) {
        this.report = report;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Order order = (Order) o;

        if (beginDate != null ? !beginDate.equals(order.beginDate) : order.beginDate != null) return false;
        if (endDate != null ? !endDate.equals(order.endDate) : order.endDate != null) return false;
        if (orderStatus != order.orderStatus) return false;
        if (message != null ? !message.equals(order.message) : order.message != null) return false;
        if (price != null ? !price.equals(order.price) : order.price != null) return false;
        if (user != null ? !user.equals(order.user) : order.user != null) return false;
        if (car != null ? !car.equals(order.car) : order.car != null) return false;
        return report != null ? report.equals(order.report) : order.report == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (beginDate != null ? beginDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (car != null ? car.hashCode() : 0);
        result = 31 * result + (report != null ? report.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(this.getId());
        sb.append(", beginDate=").append(beginDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", message='").append(message).append('\'');
        sb.append(", price=").append(price);
        sb.append(", user=").append(user);
        sb.append(", car=").append(car);
        sb.append(", report=").append(report);
        sb.append('}');
        return sb.toString();
    }

    public static class OrderBuilder{
        private final Order order;

        public OrderBuilder() {
            order = new Order();
        }

        public OrderBuilder id(Long id){
            order.setId(id);
            return this;
        }

        public OrderBuilder beginDate(LocalDate beginDate){
            order.beginDate = beginDate;
            return this;
        }

        public OrderBuilder endDate(LocalDate endDate){
            order.endDate = endDate;
            return this;
        }

        public OrderBuilder orderStatus(OrderStatus orderStatus){
            order.orderStatus = orderStatus;
            return this;
        }

        public OrderBuilder message(String message){
            order.message = message;
            return this;
        }

        public OrderBuilder user(User user){
            order.user = user;
            return this;
        }

        public OrderBuilder car(Car car){
            order.car = car;
            return this;
        }

        public OrderBuilder price(BigDecimal price){
            order.price = price;
            return this;
        }

        public OrderBuilder report(OrderReport report){
            order.report = report;
            return this;
        }

        public Order build(){
            return order;
        }
    }
}
