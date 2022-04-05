package by.lukanov.final_task.command.impl.admin;

import by.lukanov.final_task.command.Command;
import by.lukanov.final_task.command.PagePath;
import by.lukanov.final_task.command.ParameterAndAttribute;
import by.lukanov.final_task.command.Router;
import by.lukanov.final_task.entity.Car;
import by.lukanov.final_task.exception.CommandException;
import by.lukanov.final_task.exception.ServiceException;
import by.lukanov.final_task.model.service.impl.CarServiceImpl;
import by.lukanov.final_task.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class FindAllCarsCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = new CarServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();

        List<Car> cars;
        try {
            cars = carService.findAllCars();
        } catch (ServiceException e) {
            logger.error("Command exception trying find all cars", e);
            throw new CommandException(e);
        }
        request.setAttribute(ParameterAndAttribute.ALL_CARS, cars);
        router.setType(Router.Type.FORWARD);
        router.setPagePath(PagePath.ADMIN_ALL_CARS);

        return router;
    }
}
