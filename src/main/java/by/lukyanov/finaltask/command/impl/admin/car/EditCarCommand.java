package by.lukyanov.finaltask.command.impl.admin.car;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.ParameterAndAttribute;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static by.lukyanov.finaltask.command.Message.*;
import static by.lukyanov.finaltask.command.ParameterAndAttribute.*;

public class EditCarCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = new CarServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String carId = request.getParameter(CAR_ID).strip();
        String brand = request.getParameter(CAR_BRAND).strip();
        String model = request.getParameter(CAR_MODEL).strip();
        String regularPrice = request.getParameter(CAR_REGULAR_PRICE).strip();
        String salePrice = request.getParameter(CAR_SALE_PRICE).strip();
        String carActive = request.getParameter(CAR_ACTIVE).strip();
        String carCategoryId = request.getParameter(CAR_CATEGORY_ID).strip();
        String acceleration = request.getParameter(CAR_INFO_ACCELERATION).strip();
        String power = request.getParameter(CAR_INFO_POWER).strip();
        String drivetrain = request.getParameter(CAR_INFO_DRIVETRAIN).strip();
        String uploadImg = request.getParameter(UPLOAD_IMAGE) != null ? "true" : "false";

        Map<String, String> carData = new HashMap<>();
        carData.put(CAR_ID, carId);
        carData.put(CAR_BRAND, brand);
        carData.put(CAR_MODEL, model);
        carData.put(CAR_REGULAR_PRICE, regularPrice);
        carData.put(CAR_ACTIVE, carActive);
        carData.put(CAR_CATEGORY_ID, carCategoryId);
        carData.put(CAR_INFO_ACCELERATION, acceleration);
        carData.put(CAR_INFO_POWER, power);
        carData.put(CAR_INFO_DRIVETRAIN, drivetrain);
        carData.put(UPLOAD_IMAGE, uploadImg);
        if(!salePrice.isBlank()){
            carData.put(ParameterAndAttribute.CAR_SALE_PRICE, salePrice);
        }
        try (InputStream is = request.getPart(CAR_IMAGE).getInputStream()){
            if (carService.updateCar(carData, is)) {
                router.setPagePath(PagePath.ADMIN_SUCCESS_PAGE);
                router.setType(Router.Type.REDIRECT);
            } else {
                request.setAttribute(ParameterAndAttribute.MESSAGE, CAR_NOT_EDITED);
                router.setPagePath(PagePath.ADMIN_FAIL_PAGE);
            }
        } catch (ServiceException | ServletException | IOException e) {
            logger.error("Command exception trying edit car", e);
            throw new CommandException(e);
        }
        return router;
    }
}
