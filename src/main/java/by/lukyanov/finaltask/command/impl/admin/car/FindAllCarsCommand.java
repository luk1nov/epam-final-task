package by.lukyanov.finaltask.command.impl.admin.car;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.ParameterAttributeName;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.lukyanov.finaltask.command.PagePath.TO_ALL_CARS;
import static by.lukyanov.finaltask.command.ParameterAttributeName.CURRENT_PAGE;

public class FindAllCarsCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = new CarServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        session.setAttribute(CURRENT_PAGE, TO_ALL_CARS);
        try {
            List<Car> cars = carService.findAllCars();
            request.setAttribute(ParameterAttributeName.ALL_CARS, cars);
            router.setPagePath(PagePath.ADMIN_ALL_CARS);
        } catch (ServiceException e) {
            logger.error("Command exception trying find all cars", e);
            throw new CommandException(e);
        }
        return router;
    }
}
