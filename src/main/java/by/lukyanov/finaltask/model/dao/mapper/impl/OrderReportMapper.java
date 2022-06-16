package by.lukyanov.finaltask.model.dao.mapper.impl;

import by.lukyanov.finaltask.entity.OrderReport;
import by.lukyanov.finaltask.entity.OrderReportStatus;
import by.lukyanov.finaltask.model.dao.mapper.RowMapper;
import by.lukyanov.finaltask.util.ImageEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static by.lukyanov.finaltask.model.dao.ColumnName.*;

public class OrderReportMapper implements RowMapper<OrderReport> {
    private static final Logger logger = LogManager.getLogger();
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
    public Optional<OrderReport> mapRow(ResultSet rs) {
        ImageEncoder imageEncoder = ImageEncoder.getInstance();
        Optional<OrderReport> optionalOrderReport;
        try {
            OrderReport report = new OrderReport();
            report.setPhoto(imageEncoder.decodeImage(rs.getBytes(REPORT_PHOTO)));
            report.setReportStatus(OrderReportStatus.valueOf(rs.getString(REPORT_STATUS)));
            String reportText = rs.getString(REPORT_TEXT);
            if(reportText != null){
                report.setReportText(reportText);
            }
            optionalOrderReport = Optional.of(report);
        } catch (SQLException e){
            logger.error("Can not read resultset", e);
            optionalOrderReport = Optional.empty();
        }
        return optionalOrderReport;
    }
}
