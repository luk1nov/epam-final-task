package by.lukyanov.finaltask.model.dao.impl;

import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.mapper.impl.CarRowMapper;
import by.lukyanov.finaltask.model.connection.ConnectionPool;
import by.lukyanov.finaltask.model.dao.CarDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDaoImpl implements CarDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SQL_FIND_ALL_CARS = "SELECT cars.car_id, cars.brand, cars.model, cars.vin_code, cars.regular_price, cars.sale_price, cars.is_active, cars.image, car_info.acceleration, car_info.power, car_info.drivetrain, car_category.car_category_title FROM cars LEFT JOIN car_info ON cars.car_id = car_info.cars_car_id INNER JOIN car_category on cars.car_category_car_category_id = car_category.car_category_id ORDER BY cars.car_id LIMIT ? OFFSET ?";
    private static final String SQL_FIND_CARS_BY_ACTIVE_STATUS = "SELECT cars.car_id, cars.brand, cars.model, cars.vin_code, cars.regular_price, cars.sale_price, cars.is_active, cars.image, car_info.acceleration, car_info.power, car_info.drivetrain, car_category.car_category_title FROM cars LEFT JOIN car_info ON cars.car_id = car_info.cars_car_id INNER JOIN car_category on cars.car_category_car_category_id = car_category.car_category_id WHERE cars.is_active = ? ORDER BY cars.car_id LIMIT ? OFFSET ?";
    private static final String SQL_FIND_CAR_BY_ID = "SELECT cars.car_id, cars.brand, cars.model, cars.vin_code, cars.regular_price, cars.sale_price, cars.is_active, cars.image, car_info.acceleration, car_info.power, car_info.drivetrain, car_category.car_category_title FROM cars LEFT JOIN car_info ON cars.car_id = car_info.cars_car_id INNER JOIN car_category on cars.car_category_car_category_id = car_category.car_category_id WHERE car_id = ?";
    private static final String SQL_INSERT_NEW_CAR = "INSERT INTO cars (brand,model,vin_code,regular_price,sale_price,is_active, image, car_category_car_category_id) values(?,?,?,?,?,?,?)";
    private static final String SQL_INSERT_NEW_CAR_INFO = "INSERT INTO car_info (acceleration,power,drivetrain,cars_car_id) values(?,?,?,?)";
    private static final String SQL_UPDATE_CAR_BY_ID = "UPDATE cars SET brand = ?, model = ?, vin_code = ?, regular_price = ?, sale_price = ?, is_active = ?, car_category_car_category_id = ? WHERE car_id = ?";
    private static final String SQL_UPDATE_CAR_WITH_IMAGE_BY_ID = "UPDATE cars SET brand = ?, model = ?, vin_code = ?, regular_price = ?, sale_price = ?, is_active = ?, car_category_car_category_id = ?, image = ? WHERE car_id = ?";
    private static final String SQL_UPDATE_CAR_INFO_BY_CAR_ID = "UPDATE car_info SET acceleration = ?, power = ?, drivetrain = ? WHERE cars_car_id = ?";
    private static final String SQL_FIND_CARS_BY_CATEGORY_ID = "SELECT cars.car_id, cars.brand, cars.model, cars.vin_code, cars.regular_price, cars.sale_price, cars.is_active, cars.image, car_info.acceleration, car_info.power, car_info.drivetrain, car_category.car_category_title FROM cars LEFT JOIN car_info ON cars.car_id = car_info.cars_car_id INNER JOIN car_category on cars.car_category_car_category_id = car_category.car_category_id WHERE car_category_car_category_id = ? LIMIT ? OFFSET ?";
    private static final String SQL_DELETE_CAR_BY_ID = "DELETE FROM cars WHERE car_id = ?";
    private static final String SQL_UPDATE_STATUS_CAR_BY_ID = "UPDATE cars SET is_active = ? WHERE car_id = ?";
    private static final String SQL_COUNT_ALL_CARS = "SELECT COUNT(car_id) from cars";
    private static final String SQL_COUNT_ALL_CARS_BY_ACTIVE = "SELECT COUNT(car_id) from cars WHERE is_active = ?";
    private static final String SQL_COUNT_ALL_CARS_BY_CATEGORY = "SELECT COUNT(car_id) from cars WHERE car_category_car_category_id = ?";
    private static final String SQL_SEARCH_CARS = """
            SELECT c.car_id, c.brand, c.model, c.vin_code, c.regular_price, c.sale_price, c.is_active, c.image,
            info.acceleration, info.power, info.drivetrain, cat.car_category_title
            FROM cars as c
            LEFT JOIN car_info as info
            ON c.car_id = info.cars_car_id 
            INNER JOIN car_category as cat
            ON c.car_category_car_category_id = cat.car_category_id
            WHERE c.brand like ?
                    OR c.model LIKE ?
                    OR c.vin_code LIKE ?
    """;
    private static final CarRowMapper mapper = CarRowMapper.getInstance();
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

    public boolean delete(long id) throws DaoException {
        boolean result = false;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_CAR_BY_ID)) {
            statement.setLong(1, id);
            if (statement.executeUpdate() != 0){
                result = true;
                logger.info("user deleted " + id);
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying delete car", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<Car> findAll(int limit, int offset) throws DaoException {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_CARS)){
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    Optional<Car> optionalCar = mapper.mapRow(resultSet);
                    optionalCar.ifPresent(cars::add);
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying find all cars", e);
            throw new DaoException(e);
        }
        return cars;
    }

    @Override
    public boolean update(Car car) throws DaoException {
        boolean updated;
        logger.debug(car.toString());
        try (Connection connection = pool.getConnection()){
            try (PreparedStatement carStatement = connection.prepareStatement(SQL_UPDATE_CAR_BY_ID);
            PreparedStatement carInfoStatement = connection.prepareStatement(SQL_UPDATE_CAR_INFO_BY_CAR_ID)){
                connection.setAutoCommit(false);
                carStatement.setString(1, car.getBrand());
                carStatement.setString(2, car.getModel());
                carStatement.setString(3, car.getVinCode());
                carStatement.setBigDecimal(4, car.getRegularPrice());
                carStatement.setBigDecimal(5, car.getSalePrice().isPresent() ? car.getSalePrice().get() : null);
                carStatement.setBoolean(6, car.isActive());
                carStatement.setLong(7, car.getCarCategory().getId());
                carStatement.setLong(8, car.getId());
                carStatement.executeUpdate();
                carInfoStatement.setDouble(1, car.getInfo().getAcceleration());
                carInfoStatement.setInt(2, car.getInfo().getPower());
                carInfoStatement.setString(3, car.getInfo().getDrivetrain().toString());
                carInfoStatement.setLong(4, car.getId());
                carInfoStatement.executeUpdate();
                updated = true;
                connection.commit();
            } catch (SQLException e){
                logger.error("SQL exception trying update car - rollback", e);
                connection.rollback();
                throw new SQLException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying edit car", e);
            throw new DaoException(e);
        }
        return updated;
    }

    @Override
    public Optional<Car> findCarById(long id) throws DaoException {
        Optional<Car> foundCar;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_CAR_BY_ID)){
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    foundCar = mapper.mapRow(resultSet);
                } else {
                    foundCar = Optional.empty();
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying find car by id", e);
            throw new DaoException(e);
        }
        return foundCar;
    }


    public boolean insert(Car car, InputStream carImage) throws DaoException {
        boolean result = false;
        try (Connection connection = pool.getConnection()){
            try (PreparedStatement addCarStatement = connection.prepareStatement(SQL_INSERT_NEW_CAR, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement addCarInfoStatement = connection.prepareStatement(SQL_INSERT_NEW_CAR_INFO)) {
                connection.setAutoCommit(false); // 1
                addCarStatement.setString(1, car.getBrand());
                addCarStatement.setString(2, car.getModel());
                addCarStatement.setString(3, car.getVinCode());
                addCarStatement.setBigDecimal(4, car.getRegularPrice());
                addCarStatement.setBigDecimal(5, car.getSalePrice().isPresent() ? car.getSalePrice().get() : null);
                addCarStatement.setBoolean(6, car.isActive());
                addCarStatement.setBlob(7, carImage.available() != 0 ? carImage : null);
                addCarStatement.setLong(8, car.getCarCategory().getId());
                addCarStatement.executeUpdate(); // 2
                try (ResultSet resultSetCarId = addCarStatement.getGeneratedKeys()){
                    if (resultSetCarId.next() ) {
                        int carId = resultSetCarId.getInt(1);
                        addCarInfoStatement.setDouble(1, car.getInfo().getAcceleration());
                        addCarInfoStatement.setInt(2, car.getInfo().getPower());
                        addCarInfoStatement.setString(3, car.getInfo().getDrivetrain().toString());
                        addCarInfoStatement.setLong(4, carId);
                        addCarInfoStatement.executeUpdate();
                        result = true;
                        connection.commit();
                        logger.info("successful query - commit");
                    }
                }
            } catch (SQLException | IOException e) {
                logger.error("SQL exception trying add new car - rollback", e);
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
    public boolean updateWithImage(Car car, InputStream carImage) throws DaoException {
        boolean updated;
        try (Connection connection = pool.getConnection()){
            try (PreparedStatement carStatement = connection.prepareStatement(SQL_UPDATE_CAR_WITH_IMAGE_BY_ID);
                 PreparedStatement carInfoStatement = connection.prepareStatement(SQL_UPDATE_CAR_INFO_BY_CAR_ID)){
                connection.setAutoCommit(false);
                carStatement.setString(1, car.getBrand());
                carStatement.setString(2, car.getModel());
                carStatement.setString(3, car.getVinCode());
                carStatement.setBigDecimal(4, car.getRegularPrice());
                Optional<BigDecimal> salePrice = car.getSalePrice();
                carStatement.setBigDecimal(5, salePrice.orElse(null));
                carStatement.setBoolean(6, car.isActive());
                carStatement.setLong(7, car.getCarCategory().getId());
                carStatement.setBlob(8, carImage.available() != 0 ? carImage : null);
                carStatement.setLong(9, car.getId());
                carStatement.executeUpdate();
                carInfoStatement.setDouble(1, car.getInfo().getAcceleration());
                carInfoStatement.setInt(2, car.getInfo().getPower());
                carInfoStatement.setString(3, car.getInfo().getDrivetrain().toString());
                carInfoStatement.setLong(4, car.getId());
                carInfoStatement.executeUpdate();
                updated = true;
                connection.commit();
            } catch (SQLException | IOException e){
                logger.error("Dao exception trying update car - rollback", e);
                connection.rollback();
                throw new SQLException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying edit car", e);
            throw new DaoException(e);
        }
        return updated;
    }

    @Override
    public List<Car> findCarsByCategoryId(long id, int limit, int offset) throws DaoException {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_CARS_BY_CATEGORY_ID)){
            statement.setLong(1, id);
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    Optional<Car> optionalCar = mapper.mapRow(resultSet);
                    optionalCar.ifPresent(cars::add);
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying find all cars", e);
            throw new DaoException(e);
        }
        return cars;
    }

    @Override
    public boolean changeCarActiveById(long id, boolean isActive) throws DaoException {
        boolean result = false;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_STATUS_CAR_BY_ID)){
            statement.setBoolean(1, isActive);
            statement.setLong(2, id);
            if (statement.executeUpdate() != 0){
                result = true;
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying change car active status", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<Car> findCarsByActive(boolean active, int limit, int offset) throws DaoException {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_CARS_BY_ACTIVE_STATUS)) {
            statement.setBoolean(1, active);
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<Car> optionalCar = mapper.mapRow(resultSet);
                    optionalCar.ifPresent(cars::add);
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying find completed orders", e);
            throw new DaoException(e);
        }
        return cars;
    }

    @Override
    public int countAllCars() throws DaoException {
        int carsCount = 0;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_COUNT_ALL_CARS);
             ResultSet rs = statement.executeQuery()){
            if(rs.next()){
                carsCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying count all cars", e);
            throw new DaoException(e);
        }
        return carsCount;
    }

    @Override
    public int countAllCarsByActive(Boolean active) throws DaoException {
        int orderCount = 0;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_COUNT_ALL_CARS_BY_ACTIVE)){
            statement.setBoolean(1, active);
            try (ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    orderCount = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying count cars by active", e);
            throw new DaoException(e);
        }
        return orderCount;
    }

    @Override
    public int countAllCarsByCategoryId(long categoryId) throws DaoException {
        int orderCount = 0;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_COUNT_ALL_CARS_BY_CATEGORY)){
            statement.setLong(1, categoryId);
            try (ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    orderCount = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying count cars by category", e);
            throw new DaoException(e);
        }
        return orderCount;
    }

    @Override
    public List<Car> searchCars(String searchQuery) throws DaoException {
        List<Car> cars = new ArrayList<>();
        searchQuery = generateSQLSearchQuery(searchQuery);
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SEARCH_CARS)) {
            statement.setString(1, searchQuery);
            statement.setString(2, searchQuery);
            statement.setString(3, searchQuery);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<Car> optionalCar = mapper.mapRow(resultSet);
                    optionalCar.ifPresent(cars::add);
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying search car", e);
            throw new DaoException(e);
        }
        return cars;
    }
}
