package by.lukyanov.finaltask.model.dao.impl;

import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.model.connection.ConnectionPool;
import by.lukyanov.finaltask.model.dao.CarCategoryDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.lukyanov.finaltask.model.dao.ColumnName.*;


public class CarCategoryDaoImpl implements CarCategoryDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SQL_INSERT_CAR_CATEGORY = "INSERT INTO car_category (car_category_title) VALUES (?)";
    private static final String SQL_SELECT_ALL_CAR_CATEGORIES = "SELECT car_category_id, car_category_title FROM car_category LIMIT ? OFFSET ?";
    private static final String SQL_SELECT_CAR_CATEGORY_BY_ID = "SELECT car_category_id, car_category_title FROM car_category WHERE car_category_id = ?";
    private static final String SQL_SELECT_CAR_CATEGORY_BY_TITLE = "SELECT car_category_id, car_category_title FROM car_category WHERE car_category_title = ?";
    private static final String SQL_DELETE_CAR_CATEGORY_BY_ID = "DELETE FROM car_category WHERE car_category_id = ?";
    private static final String SQL_UPDATE_CAR_CATEGORY = "UPDATE car_category SET car_category_title = ? WHERE car_category_id = ?";
    private static CarCategoryDaoImpl instance;
    private final ConnectionPool pool = ConnectionPool.getInstance();

    private CarCategoryDaoImpl() {
    }

    public static CarCategoryDaoImpl getInstance(){
        if (instance == null){
            instance = new CarCategoryDaoImpl();
        }
        return instance;
    }


    public boolean insert(CarCategory category) throws DaoException {
        boolean result = false;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_CAR_CATEGORY)) {
            statement.setString(1, category.getTitle());
            int resultRows = statement.executeUpdate();
            if (resultRows != 0){
                result = true;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        boolean result = false;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_CAR_CATEGORY_BY_ID)) {
            statement.setLong(1, id);
            int resultRows = statement.executeUpdate();
            if (resultRows != 0){
                result = true;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<CarCategory> findAll(int limit, int offset) throws DaoException {
        List<CarCategory> carCategories = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_CAR_CATEGORIES)){
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            try (ResultSet rs = statement.executeQuery()){
                while (rs.next()){
                    CarCategory category = new CarCategory(rs.getLong(CAR_CAT_ID), rs.getString(CAR_CAT_TITLE));
                    carCategories.add(category);
                }
            }
        } catch (SQLException e) {
             throw new DaoException(e);
        }
        return carCategories;
    }

    @Override
    public boolean update(CarCategory category) throws DaoException {
        boolean result = false;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_CAR_CATEGORY)){
            statement.setString(1, category.getTitle());
            statement.setLong(2, category.getId());
            int resultRows = statement.executeUpdate();
            if (resultRows != 0){
                result = true;
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying update car category", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public Optional<CarCategory> findById(long id) throws DaoException {
        Optional<CarCategory> optionalCarCategory;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CAR_CATEGORY_BY_ID)){
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()){
                if (rs.next()){
                    CarCategory category = new CarCategory(rs.getLong(CAR_CAT_ID), rs.getString(CAR_CAT_TITLE));
                    optionalCarCategory = Optional.of(category);
                } else {
                    optionalCarCategory = Optional.empty();
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying find car category by id", e);
            throw new DaoException(e);
        }
        return optionalCarCategory;
    }

    @Override
    public Optional<CarCategory> findCarCategoryByTitle(String title) throws DaoException {
        Optional<CarCategory> optionalCarCategory;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CAR_CATEGORY_BY_TITLE)){
            statement.setString(1, title);
            try (ResultSet rs = statement.executeQuery()){
                if (rs.next()){
                    CarCategory category = new CarCategory(rs.getLong(CAR_CAT_ID), rs.getString(CAR_CAT_TITLE));
                    optionalCarCategory = Optional.of(category);
                } else {
                    optionalCarCategory = Optional.empty();
                }
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying find car category by id", e);
            throw new DaoException(e);
        }
        return optionalCarCategory;
    }
}
