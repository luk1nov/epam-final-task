package by.lukyanov.finaltask.model.dao.mapper.impl;

import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.entity.CarInfo;
import by.lukyanov.finaltask.model.dao.mapper.RowMapper;
import by.lukyanov.finaltask.util.ImageEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CarRowMapper implements RowMapper<Car> {
    private static final Logger logger = LogManager.getLogger();
    private static CarRowMapper instance;

    private CarRowMapper() {
    }

    public static CarRowMapper getInstance(){
        if (instance == null){
            instance = new CarRowMapper();
        }
        return instance;
    }

    @Override
    public Optional<Car> mapRow(ResultSet resultSet) {
        ImageEncoder imageEncoder = ImageEncoder.getInstance();
        Optional<Car> optionalCar;
        try {
            Car car = new Car.CarBuilder()
                    .id(resultSet.getLong("car_id"))
                    .brand(resultSet.getString("brand"))
                    .model(resultSet.getString("model"))
                    .vin(resultSet.getString("vin_code"))
                    .regularPrice(resultSet.getBigDecimal("regular_price"))
                    .salePrice(resultSet.getString("sale_price") != null ? resultSet.getBigDecimal("sale_price") : null)
                    .active(resultSet.getBoolean("is_active"))
                    .image(imageEncoder.decodeImage(resultSet.getBytes("image")))
                    .carInfo(new CarInfo(resultSet.getDouble("acceleration"), resultSet.getInt("power"),
                            CarInfo.Drivetrain.valueOf(resultSet.getString("drivetrain").toUpperCase())))
                    .category(new CarCategory(resultSet.getString("car_category_title")))
                    .build();
            optionalCar = Optional.of(car);
        } catch (SQLException e){
            logger.error("Can not read resultset", e);
            optionalCar = Optional.empty();
        }
        return optionalCar;
    }
}
