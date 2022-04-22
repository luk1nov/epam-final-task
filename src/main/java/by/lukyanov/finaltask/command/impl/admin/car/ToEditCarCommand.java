package by.lukyanov.finaltask.command.impl.admin.car;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Message;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarCategoryServiceImpl;
import by.lukyanov.finaltask.model.service.impl.CarServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static by.lukyanov.finaltask.command.ParameterAndAttribute.*;

public class ToEditCarCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = new CarServiceImpl();
    private static final CarCategoryServiceImpl carCategoryService = new CarCategoryServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String carId = request.getParameter(CAR_ID).strip();
        try {
            Optional<Car> optionalCar = carService.findCarById(carId);
            if (optionalCar.isPresent()){
                Car car = optionalCar.get();
                logger.debug("car found " + car.getId());
                List<CarCategory> carCategoryList = carCategoryService.findAllCarCategories();
                request.setAttribute(LIST, carCategoryList);
                router.setPagePath(PagePath.ADMIN_EDIT_CAR);
                request.setAttribute(CAR, car);
            } else{
                logger.debug("car not found");
                router.setPagePath(PagePath.ADMIN_FAIL_PAGE);
                request.setAttribute(MESSAGE, Message.CAN_NOT_EDIT_USER);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying find car by id");
            throw new CommandException(e);
        }
        return router;
    }
}
