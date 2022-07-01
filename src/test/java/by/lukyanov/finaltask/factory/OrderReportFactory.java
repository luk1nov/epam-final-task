package by.lukyanov.finaltask.factory;

import by.lukyanov.finaltask.entity.OrderReport;
import by.lukyanov.finaltask.entity.OrderReportStatus;

import java.util.HashMap;
import java.util.Map;

import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class OrderReportFactory {
    public static final OrderReportStatus reportStatus = OrderReportStatus.WITHOUT_DEFECTS;
    public static final String reportText = "text";
    public static final String orderId = "1";


    public static OrderReport createOrderReport(){
        return new OrderReport(reportText, reportText, reportStatus);
    }

    public static Map<String, String> createReportData(){
        Map<String, String> reportData = new HashMap<>();
        reportData.put(ORDER_REPORT_TEXT, reportText);
        reportData.put(ORDER_REPORT_STATUS, reportStatus.name());
        reportData.put(ORDER_ID, orderId);
        return reportData;
    }
}
