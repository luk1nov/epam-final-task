package by.lukyanov.finaltask.mapper.impl;

import by.lukyanov.finaltask.entity.OrderReport;
import by.lukyanov.finaltask.entity.OrderReportStatus;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.mapper.RowMapper;
import by.lukyanov.finaltask.util.ImageEncoder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class OrderReportMapper implements RowMapper<OrderReport> {
    private static OrderReportMapper instance;

    private OrderReportMapper() {
    }

    public static OrderReportMapper getInstance(){
        if(instance == null){
            instance = new OrderReportMapper();
        }
        return instance;
    }
    @Override
    public Optional<OrderReport> mapRow(ResultSet rs) throws SQLException, DaoException {
        OrderReport report = new OrderReport();
        report.setPhoto(ImageEncoder.getInstance().decodeBlob(rs.getBlob("report_photo")));
        report.setReportStatus(OrderReportStatus.valueOf(rs.getString("report_status")));
        String reportText = rs.getString("report_text");
        if(reportText != null){
            report.setReportText(reportText);
        }
        return Optional.of(report);
    }
}
