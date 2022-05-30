package by.lukyanov.finaltask.command.impl.admin.car;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarServiceImpl;
import by.lukyanov.finaltask.util.ResultCounter;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.lukyanov.finaltask.command.PagePath.ADMIN_REPAIRING_CARS;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class FindCarsOnRepairCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = new CarServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(ADMIN_REPAIRING_CARS);
        String currentResultPage = request.getParameter(RESULT_PAGE);
        try {
            int pagesCount = ResultCounter.countPages(carService.countAllCarsByActive(false));
            if(currentResultPage == null || currentResultPage.isBlank()){
                currentResultPage = "1";
            }
            request.setAttribute(PAGES_COUNT, pagesCount);
            request.setAttribute(RESULT_PAGE, currentResultPage);
            List<Car> cars = carService.findCarsByActiveStatus(false, currentResultPage);
            request.setAttribute(LIST, cars);
        } catch (ServiceException e) {
            logger.error("Command exception trying find cars on repair", e);
            throw new CommandException(e);
        }
        return router;
    }
}
