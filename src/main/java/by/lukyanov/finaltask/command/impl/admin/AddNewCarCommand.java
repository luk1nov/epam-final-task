package by.lukyanov.finaltask.command.impl.admin;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.ParameterAndAttribute;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static by.lukyanov.finaltask.command.Message.*;
import static by.lukyanov.finaltask.command.ParameterAndAttribute.*;public class AddNewCarCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = new CarServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        boolean result;
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
            carData.put(ParameterAndAttribute.CAR_SALE_PRICE, salePrice);
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
            request.setAttribute(MESSAGE, CAR_NOT_ADDED);
        }
        return router;
    }
}
