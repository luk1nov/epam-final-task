package by.lukyanov.finaltask.command.impl.admin.car;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static by.lukyanov.finaltask.command.Message.CAR_NOT_ADDED;
import static by.lukyanov.finaltask.command.ParameterAndAttribute.*;

public class AddNewCarCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = new CarServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        boolean result;
        String brand = request.getParameter(CAR_BRAND).strip();
        String model = request.getParameter(CAR_MODEL).strip();
        String regularPrice = request.getParameter(CAR_REGULAR_PRICE).strip();
        String salePrice = request.getParameter(CAR_SALE_PRICE).strip();
        String carActive = request.getParameter(CAR_ACTIVE).strip();
        String acceleration = request.getParameter(CAR_INFO_ACCELERATION).strip();
        String power = request.getParameter(CAR_INFO_POWER).strip();
        String drivetrain = request.getParameter(CAR_INFO_DRIVETRAIN).strip();
        Map<String, String> carData = new HashMap<>();
        carData.put(CAR_BRAND, brand);
        carData.put(CAR_MODEL, model);
        carData.put(CAR_REGULAR_PRICE, regularPrice);
        carData.put(CAR_ACTIVE, carActive);
        carData.put(CAR_INFO_ACCELERATION, acceleration);
        carData.put(CAR_INFO_POWER, power);
        carData.put(CAR_INFO_DRIVETRAIN, drivetrain);
        if(!salePrice.isBlank()){
            carData.put(CAR_SALE_PRICE, salePrice);
        }
        try {
            Part filePart = request.getPart(CAR_IMAGE);
            InputStream is = null;
            if (filePart != null){
                is = filePart.getInputStream();
            }
            if(carService.addCar(carData, is)){
                router.setPagePath(PagePath.SUCCESS_PAGE);
                router.setType(Router.Type.REDIRECT);
            } else {
                router.setPagePath(PagePath.FAIL_PAGE);
                request.setAttribute(MESSAGE, CAR_NOT_ADDED);
            }
        } catch (IOException e) {
            logger.error("Command exception trying to get input stream from part", e);
            throw new CommandException(e);
        } catch (ServletException e) {
            logger.error("Command exception trying to get part from request", e);
            throw new CommandException(e);
        } catch (ServiceException e) {
            logger.error("Command exception trying add car", e);
            throw new CommandException(e);
        }
        return router;
    }
}
