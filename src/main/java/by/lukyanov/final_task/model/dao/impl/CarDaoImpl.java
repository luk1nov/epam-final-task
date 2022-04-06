package by.lukyanov.final_task.model.dao.impl;

import by.lukyanov.final_task.entity.Car;
import by.lukyanov.final_task.entity.CarInfo;
import by.lukyanov.final_task.exception.DaoException;
import by.lukyanov.final_task.model.connection.ConnectionPool;
import by.lukyanov.final_task.model.dao.CarDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SQL_FIND_ALL_CARS = "SELECT cars.car_id, cars.brand, cars.model, cars.regular_price, cars.sale_price, cars.is_active, car_info.acceleration, car_info.power, car_info.drivetrain FROM cars LEFT JOIN car_info ON cars.car_id = car_info.cars_car_id";
    private static final String SQL_INSERT_NEW_CAR = "INSERT INTO cars (brand,model,regular_price,sale_price,is_active) values(?,?,?,?,?)";
    private static final String SQL_INSERT_NEW_CAR_INFO = "INSERT INTO car_info (acceleration,power,drivetrain,cars_car_id) values(?,?,?,?)";
    private static CarDaoImpl instance;
    private final ConnectionPool pool = ConnectionPool.getInstance();


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
        boolean result = false;
        try (Connection connection = pool.getConnection()){
            try (PreparedStatement addCarStatement = connection.prepareStatement(SQL_INSERT_NEW_CAR, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement addCarInfoStatement = connection.prepareStatement(SQL_INSERT_NEW_CAR_INFO)) {
                connection.setAutoCommit(false); // 1
                addCarStatement.setString(1, car.getBrand());
                addCarStatement.setString(2, car.getModel());
                addCarStatement.setString(3, car.getRegularPrice().toString());
                addCarStatement.setString(4, car.getSalePrice().isPresent() ? car.getSalePrice().get().toString() : null);
                addCarStatement.setString(5, car.isActive() ? "1" : "0");
                addCarStatement.executeUpdate(); // 2
                ResultSet resultSetCarId = addCarStatement.getGeneratedKeys();
                if (resultSetCarId.next() && car.getInfo() != null) {
                    int carId = resultSetCarId.getInt(1);
                    addCarInfoStatement.setString(1, String.valueOf(car.getInfo().getAcceleration()));
                    addCarInfoStatement.setString(2, String.valueOf(car.getInfo().getPower()));
                    addCarInfoStatement.setString(3, car.getInfo().getDrivetrain().toString());
                    addCarInfoStatement.setString(4, String.valueOf(carId));
                    int resultSetCarInfo = addCarInfoStatement.executeUpdate();
                    if (resultSetCarInfo != 0){
                        result = true;
                        connection.commit();
                    } else {
                        logger.info("Car didnt add since car info adding failed");
                        connection.rollback();
                    }
                } else {
                    logger.info("Car didnt add since car adding failed");
                    connection.rollback();
                }
            } catch (SQLException e) {
                logger.error("Dao exception trying add new car", e);
                connection.rollback();
                throw new SQLException(e);
            } finally {
                connection.setAutoCommit(true); // 4
            }
        } catch (SQLException e){
            logger.error("Dao exception trying add new car", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public boolean delete(Car car) throws DaoException {
        return false;
    }

    @Override
    public List<Car> findAll() throws DaoException {
        List<Car> cars = new ArrayList<>();
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
