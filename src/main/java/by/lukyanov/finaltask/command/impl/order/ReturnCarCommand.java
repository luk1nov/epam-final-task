package by.lukyanov.finaltask.command.impl.order;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.OrderServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static by.lukyanov.finaltask.command.Message.CAR_NOT_ADDED;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class ReturnCarCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderServiceImpl orderService = new OrderServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        Map<String, String> reportData = requestAttrToReportData(request);
        try (InputStream is = request.getPart(ORDER_REPORT_PHOTO).getInputStream()){
            if(orderService.completeOrder(reportData, is)){
                router.setPagePath(PagePath.TO_USER_ORDERS);
                router.setType(Router.Type.REDIRECT);
            } else {
                router.setPagePath(PagePath.MAIN_PAGE);
                //TODO add fmt message
            }
        } catch (IOException e) {
            logger.error("Command exception trying to get input stream from part", e);
            throw new CommandException(e);
        } catch (ServletException e) {
            logger.error("Command exception trying to get part from request", e);
            throw new CommandException(e);
        } catch (ServiceException e) {
            logger.error("Command exception trying complete order", e);
            throw new CommandException(e);
        }
        return router;
    }

    private Map<String, String> requestAttrToReportData(HttpServletRequest request){
        Map<String, String> reportData = new HashMap<>();
        reportData.put(ORDER_REPORT_STATUS, request.getParameter(ORDER_REPORT_STATUS));
        reportData.put(ORDER_REPORT_TEXT, request.getParameter(ORDER_REPORT_TEXT));
        reportData.put(ORDER_ID, request.getParameter(ORDER_ID));
        return reportData;
    }
}
