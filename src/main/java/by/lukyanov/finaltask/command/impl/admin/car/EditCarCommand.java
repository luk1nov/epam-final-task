package by.lukyanov.finaltask.command.impl.admin.car;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.ParameterAttributeName;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.lukyanov.finaltask.command.Message.*;
import static by.lukyanov.finaltask.command.PagePath.TO_ADMIN_ALL_CARS;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class EditCarCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = CarServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        String vinCode = request.getParameter(CAR_VIN_CODE);
        String carId = request.getParameter(CAR_ID);
        Router router = new Router(currentPage);
        Map<String, String> carData = requestAttrToCarData(request);
        try (InputStream is = request.getPart(CAR_IMAGE).getInputStream()){
            if (checkDuplicateByVinCode(vinCode, carId)){
                request.setAttribute(MESSAGE, CAR_EXISTS);
            } else if (!carService.updateCar(carData, is)) {
                request.setAttribute(MESSAGE, CAR_NOT_EDITED);
            } else {
                router.setType(Router.Type.REDIRECT);
                router.setPagePath(generateUrlWithAttr(TO_ADMIN_ALL_CARS, MESSAGE_ATTR, CAR_EDITED));
            }
        } catch (ServiceException | ServletException | IOException e) {
            logger.error("Command exception trying edit car", e);
            throw new CommandException(e);
        }
        return router;
    }

    private Map<String, String> requestAttrToCarData(HttpServletRequest request){
        String salePrice = request.getParameter(CAR_SALE_PRICE);
        Map<String, String> carData = new HashMap<>();
        carData.put(CAR_ID, request.getParameter(CAR_ID));
        carData.put(CAR_BRAND, request.getParameter(CAR_BRAND));
        carData.put(CAR_MODEL, request.getParameter(CAR_MODEL));
        carData.put(CAR_VIN_CODE, request.getParameter(CAR_VIN_CODE));
        carData.put(CAR_REGULAR_PRICE, request.getParameter(CAR_REGULAR_PRICE));
        carData.put(CAR_ACTIVE, request.getParameter(CAR_ACTIVE));
        carData.put(CAR_CATEGORY_ID, request.getParameter(CAR_CATEGORY_ID));
        carData.put(CAR_INFO_ACCELERATION, request.getParameter(CAR_INFO_ACCELERATION));
        carData.put(CAR_INFO_POWER, request.getParameter(CAR_INFO_POWER));
        carData.put(CAR_INFO_DRIVETRAIN, request.getParameter(CAR_INFO_DRIVETRAIN));
        carData.put(UPLOAD_IMAGE, request.getParameter(UPLOAD_IMAGE) != null ? "true" : "false");
        if(!salePrice.isBlank()){
            carData.put(ParameterAttributeName.CAR_SALE_PRICE, salePrice);
        }
        return carData;
    }

    private boolean checkDuplicateByVinCode(String vinCode, String carId) throws ServiceException {
        boolean isDuplicate = false;
        Optional<Car> optionalCar = carService.findCarByVinCode(vinCode);
        if (optionalCar.isPresent()){
            Car car = optionalCar.get();
            isDuplicate = !carId.equals(String.valueOf(car.getId()));
        }
        return isDuplicate;
    }
}
