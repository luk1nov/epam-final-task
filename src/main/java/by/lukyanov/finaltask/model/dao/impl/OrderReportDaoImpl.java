package by.lukyanov.finaltask.model.dao.impl;

import by.lukyanov.finaltask.entity.OrderReport;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.model.dao.OrderReportDao;

import java.util.List;

public class OrderReportDaoImpl implements OrderReportDao {
    @Override
    public boolean delete(long id) throws DaoException {
        return false;
    }

    @Override
    public List<OrderReport> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean update(OrderReport orderReport) throws DaoException {
        return false;
    }
}
