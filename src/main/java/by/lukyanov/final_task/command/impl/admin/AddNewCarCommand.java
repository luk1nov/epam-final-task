package by.lukyanov.final_task.command.impl.admin;

import by.lukyanov.final_task.command.Command;
import by.lukyanov.final_task.command.PagePath;
import by.lukyanov.final_task.command.ParameterAndAttribute;
import by.lukyanov.final_task.command.Router;
import by.lukyanov.final_task.exception.CommandException;
import by.lukyanov.final_task.exception.ServiceException;
import by.lukyanov.final_task.model.service.impl.CarServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class AddNewCarCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = new CarServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        boolean result;
        //todo role access filter
        //request executing, but filter filters only forward & redirects in the end of page

        String brand = request.getParameter(ParameterAndAttribute.CAR_BRAND).strip();
        String model = request.getParameter(ParameterAndAttribute.CAR_MODEL).strip();
        String regularPrice = request.getParameter(ParameterAndAttribute.CAR_REGULAR_PRICE).strip();
        String salePrice = request.getParameter(ParameterAndAttribute.CAR_SALE_PRICE).strip();
        String carActive = request.getParameter(ParameterAndAttribute.CAR_ACTIVE).strip();
        String acceleration = request.getParameter(ParameterAndAttribute.CAR_INFO_ACCELERATION).strip();
        String power = request.getParameter(ParameterAndAttribute.CAR_INFO_POWER).strip();
        String drivetrain = request.getParameter(ParameterAndAttribute.CAR_INFO_DRIVETRAIN).strip();

        Map<String, String> carData = new HashMap<>();
        carData.put(ParameterAndAttribute.CAR_BRAND, brand);
        carData.put(ParameterAndAttribute.CAR_MODEL, model);
        carData.put(ParameterAndAttribute.CAR_REGULAR_PRICE, regularPrice);
        carData.put(ParameterAndAttribute.CAR_ACTIVE, carActive);
        carData.put(ParameterAndAttribute.CAR_INFO_ACCELERATION, acceleration);
        carData.put(ParameterAndAttribute.CAR_INFO_POWER, power);
        carData.put(ParameterAndAttribute.CAR_INFO_DRIVETRAIN, drivetrain);
        if(!salePrice.isBlank()){
            carData.put(ParameterAndAttribute.CAR_SALE_PRICE, brand);
        }

        try {
            result = carService.addCar(carData);
        } catch (ServiceException e) {
            logger.error("Command exception trying add car", e);
            throw new CommandException(e);
        }
        if(result){
            router.setPagePath(PagePath.SUCCESS_PAGE);
            router.setType(Router.Type.REDIRECT);
        } else {
            router.setPagePath(PagePath.FAIL_PAGE);
            request.setAttribute(ParameterAndAttribute.MESSAGE, "car not added");
        }
        return router;
    }
}
