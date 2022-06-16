package by.lukyanov.finaltask.command.impl.admin.car;

import by.lukyanov.finaltask.command.Command;
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

public class AddNewCarCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = CarServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        String vinCode = request.getParameter(CAR_VIN_CODE);
        Map<String, String> carData = requestAttrToCarData(request);
        Router router = new Router(currentPage);
        try (InputStream is = request.getPart(CAR_IMAGE).getInputStream()){
            Optional<Car> optionalCar = carService.findCarByVinCode(vinCode);
            if(optionalCar.isPresent()){
                forwardRequest(request);
                request.setAttribute(MESSAGE, CAR_EXISTS);
            } else if (carService.addCar(carData, is)){
                router.setType(Router.Type.REDIRECT);
                router.setPagePath(generateUrlWithAttr(TO_ADMIN_ALL_CARS, MESSAGE_ATTR, CAR_ADDED));
            } else {
                forwardRequest(request);
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

    private Map<String, String> requestAttrToCarData(HttpServletRequest request){
        Map<String, String> carData = new HashMap<>();
        String salePrice = request.getParameter(CAR_SALE_PRICE);
        carData.put(CAR_BRAND, request.getParameter(CAR_BRAND));
        carData.put(CAR_MODEL, request.getParameter(CAR_MODEL));
        carData.put(CAR_VIN_CODE, request.getParameter(CAR_VIN_CODE));
        carData.put(CAR_REGULAR_PRICE, request.getParameter(CAR_REGULAR_PRICE));
        carData.put(CAR_ACTIVE, request.getParameter(CAR_ACTIVE));
        carData.put(CAR_CATEGORY_ID, request.getParameter(CAR_CATEGORY_ID));
        carData.put(CAR_INFO_ACCELERATION, request.getParameter(CAR_INFO_ACCELERATION));
        carData.put(CAR_INFO_POWER, request.getParameter(CAR_INFO_POWER));
        carData.put(CAR_INFO_DRIVETRAIN, request.getParameter(CAR_INFO_DRIVETRAIN));
        if(!salePrice.isBlank()){
            carData.put(CAR_SALE_PRICE, salePrice);
        }
        return carData;
    }

    private void forwardRequest(HttpServletRequest request){
        request.setAttribute(CAR_BRAND, request.getParameter(CAR_BRAND));
        request.setAttribute(CAR_MODEL, request.getParameter(CAR_MODEL));
        request.setAttribute(CAR_VIN_CODE, request.getParameter(CAR_VIN_CODE));
        request.setAttribute(CAR_REGULAR_PRICE, request.getParameter(CAR_REGULAR_PRICE));
        request.setAttribute(CAR_SALE_PRICE, request.getParameter(CAR_SALE_PRICE));
        request.setAttribute(CAR_ACTIVE, request.getParameter(CAR_ACTIVE));
        request.setAttribute(CAR_CATEGORY_ID, request.getParameter(CAR_CATEGORY_ID));
        request.setAttribute(CAR_INFO_ACCELERATION, request.getParameter(CAR_INFO_ACCELERATION));
        request.setAttribute(CAR_INFO_POWER, request.getParameter(CAR_INFO_POWER));
        request.setAttribute(CAR_INFO_DRIVETRAIN, request.getParameter(CAR_INFO_DRIVETRAIN));
    }

}
