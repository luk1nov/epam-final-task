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

import static by.lukyanov.finaltask.model.dao.ColumnName.*;

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
                    .id(resultSet.getLong(CAR_ID))
                    .brand(resultSet.getString(CAR_BRAND))
                    .model(resultSet.getString(CAR_MODEL))
                    .vin(resultSet.getString(CAR_VIN_CODE))
                    .regularPrice(resultSet.getBigDecimal(CAR_REGULAR_PRICE))
                    .salePrice(resultSet.getString(CAR_SALE_PRICE) != null ? resultSet.getBigDecimal(CAR_SALE_PRICE) : null)
                    .active(resultSet.getBoolean(CAR_IS_ACTIVE))
                    .image(imageEncoder.decodeImage(resultSet.getBytes(CAR_IMG)))
                    .carInfo(new CarInfo(resultSet.getDouble(CAR_INFO_ACCELERATION), resultSet.getInt(CAR_INFO_POWER),
                            CarInfo.Drivetrain.valueOf(resultSet.getString(CAR_INFO_DRIVETRAIN).toUpperCase())))
                    .category(new CarCategory(resultSet.getString(CAR_CAT_TITLE)))
                    .build();
            optionalCar = Optional.of(car);
        } catch (SQLException e){
            logger.error("Can not read resultset", e);
            optionalCar = Optional.empty();
        }
        return optionalCar;
    }
}
