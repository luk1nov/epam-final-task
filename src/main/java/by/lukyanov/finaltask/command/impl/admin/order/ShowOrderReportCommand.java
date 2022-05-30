package by.lukyanov.finaltask.command.impl.admin.order;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.ParameterAttributeName;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.OrderReport;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Optional;

import static by.lukyanov.finaltask.command.Message.REPORT_NOT_FOUND;
import static by.lukyanov.finaltask.command.PagePath.ADMIN_ORDER_REPORT;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class ShowOrderReportCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderServiceImpl orderService = new OrderServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String path = (String) session.getAttribute(CURRENT_PAGE);
        Router router = new Router(ADMIN_ORDER_REPORT);
        String reportId = request.getParameter(ORDER_REPORT_ID);
        String orderId = request.getParameter(ORDER_ID);
        try {
            Optional<OrderReport> optionalOrderReport = orderService.findOrderReportById(reportId);
            if (optionalOrderReport.isPresent()){
                request.setAttribute(ORDER_REPORT, optionalOrderReport.get());
                request.setAttribute(ORDER_ID, orderId);
            } else {
                path += MESSAGE_ATTR + REPORT_NOT_FOUND;
                router.setPagePath(path);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying find order report by id", e);
            throw new CommandException(e);
        }
        return router;
    }
}
