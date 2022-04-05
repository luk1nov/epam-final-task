package by.lukanov.final_task.model.dao.impl;

import by.lukanov.final_task.entity.Car;
import by.lukanov.final_task.entity.CarInfo;
import by.lukanov.final_task.exception.DaoException;
import by.lukanov.final_task.model.connection.ConnectionPool;
import by.lukanov.final_task.model.dao.CarDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {
    private static final Logger logger = LogManager.getLogger();
    private static CarDaoImpl instance;
    private static final String SQL_FIND_ALL_CARS = "SELECT cars.car_id, cars.brand, cars.model, cars.regular_price, cars.sale_price, cars.is_active, car_info.acceleration, car_info.power, car_info.drivetrain FROM cars LEFT JOIN car_info ON cars.car_id = car_info.cars_car_id";


    private CarDaoImpl() {
    }

    public static CarDaoImpl getInstance(){
        if (instance == null){
            instance = new CarDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean insert(Car car) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Car car) throws DaoException {
        return false;
    }

    @Override
    public List<Car> findAll() throws DaoException {
        List<Car> cars = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_CARS);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()){
                Car car = new Car.CarBuilder()
                        .id(Long.parseLong(resultSet.getString(1)))
                        .brand(resultSet.getString(2))
                        .model(resultSet.getString(3))
                        .regularPrice(BigDecimal.valueOf(Double.parseDouble(resultSet.getString(4))))
                        .active(resultSet.getBoolean(6))
                        .build();

                CarInfo info = new CarInfo();
                info.setAcceleration(resultSet.getString(7) != null ? resultSet.getDouble(7) : null);
                info.setPower(resultSet.getString(8) != null ? resultSet.getInt(8) : null);
                info.setDrivetrain(resultSet.getString(9) != null ? CarInfo.Drivetrain.valueOf(resultSet.getString(9).toUpperCase()) : null);
                car.setInfo(info);
                String salePrice = resultSet.getString(5);
                if(salePrice != null){
                    car.setSalePrice(BigDecimal.valueOf(Double.parseDouble(salePrice)));
                }
                cars.add(car);
            }
        } catch (SQLException e) {
            logger.error("SQL exception trying find all cars", e);
            throw new DaoException(e);
        }
        return cars;
    }

    @Override
    public boolean update(Car car) throws DaoException {
        return false;
    }
}
