package by.lukyanov.final_task.entity;

import java.math.BigDecimal;
import java.util.Optional;

public final class Car extends AbstractEntity{
    private String brand;
    private String model;
    private BigDecimal regularPrice;
    private BigDecimal salePrice;
    private boolean active;
    private CarInfo info;

    public Car() {
        active = true;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(BigDecimal regularPrice) {
        this.regularPrice = regularPrice;
    }

    public Optional<BigDecimal> getSalePrice() {
        return Optional.ofNullable(salePrice);
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public CarInfo getInfo() {
        return info;
    }

    public void setInfo(CarInfo info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Car car = (Car) o;
        if (active != car.active) return false;
        if (brand != null ? !brand.equals(car.brand) : car.brand != null) return false;
        if (model != null ? !model.equals(car.model) : car.model != null) return false;
        if (regularPrice != null ? !regularPrice.equals(car.regularPrice) : car.regularPrice != null) return false;
        if (salePrice != null ? !salePrice.equals(car.salePrice) : car.salePrice != null) return false;
        return info != null ? info.equals(car.info) : car.info == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (regularPrice != null ? regularPrice.hashCode() : 0);
        result = 31 * result + (salePrice != null ? salePrice.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Car{");
        sb.append("brand='").append(brand).append('\'');
        sb.append(", model='").append(model).append('\'');
        sb.append(", regularPrice=").append(regularPrice);
        sb.append(", salePrice=").append(salePrice);
        sb.append(", active=").append(active);
        sb.append(", info=").append(info);
        sb.append('}');
        return sb.toString();
    }

    public static class CarBuilder{
        private final Car car;

        public CarBuilder() {
            car = new Car();
        }

        public CarBuilder id(Long id){
            car.setId(id);
            return this;
        }

        public CarBuilder brand(String brand){
            car.brand = brand;
            return this;
        }

        public CarBuilder model(String model){
            car.model = model;
            return this;
        }

        public CarBuilder regularPrice(BigDecimal regularPrice){
            car.regularPrice = regularPrice;
            return this;
        }

        public CarBuilder salePrice(BigDecimal salePrice){
            car.salePrice = salePrice;
            return this;
        }

        public CarBuilder active(boolean active){
            car.active = active;
            return this;
        }

        public CarBuilder carInfo(CarInfo info){
            car.info = info;
            return this;
        }

        public Car build(){
            return car;
        }
    }
}
