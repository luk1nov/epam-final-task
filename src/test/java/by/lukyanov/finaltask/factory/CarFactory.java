package by.lukyanov.finaltask.factory;

import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.entity.CarInfo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class CarFactory {
    private static final long id = 1L;
    private static final String categoryTitle = "Car";
    private static final String brand = "Audi";
    private static final String model = "A5";
    private static final BigDecimal regularPrice = new BigDecimal(1000);
    private static final String vinCode = "ZFA22304575556869";
    private static final boolean active = true;
    private static final double acceleration = 3.3;
    private static final int power = 532;
    private static final CarInfo.Drivetrain drivetrain = CarInfo.Drivetrain.AWD;

    public static Car createCar(){
        return new Car.CarBuilder()
                .id(id)
                .brand(brand)
                .model(model)
                .regularPrice(regularPrice)
                .category(new CarCategory(id, categoryTitle))
                .vin(vinCode)
                .active(active)
                .carInfo(new CarInfo(acceleration, power, drivetrain))
                .build();
    }

    public static Map<String, String> createCarData(){
        Map<String, String> carData = new HashMap<>();
        carData.put(CAR_ID, String.valueOf(id));
        carData.put(CAR_BRAND, brand);
        carData.put(CAR_MODEL, model);
        carData.put(CAR_VIN_CODE, vinCode);
        carData.put(CAR_REGULAR_PRICE, regularPrice.toString());
        carData.put(CAR_ACTIVE, String.valueOf(active));
        carData.put(CAR_INFO_ACCELERATION, String.valueOf(acceleration));
        carData.put(CAR_INFO_POWER, String.valueOf(power));
        carData.put(CAR_INFO_DRIVETRAIN, drivetrain.name());
        carData.put(CAR_CATEGORY_ID, String.valueOf(id));
        return carData;
    }


}
