package by.lukyanov.finaltask.command.impl.admin.car;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.command.impl.util.ContextCategoryUploader;
import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.lukyanov.finaltask.command.Message.CAR_NOT_EXISTS;
import static by.lukyanov.finaltask.command.PagePath.ADMIN_EDIT_CAR;
import static by.lukyanov.finaltask.command.PagePath.TO_ADMIN_EDIT_CAR;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class ToEditCarCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = CarServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ContextCategoryUploader uploader = ContextCategoryUploader.getInstance();
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Router router = new Router(ADMIN_EDIT_CAR);
        String carId = request.getParameter(CAR_ID);
        try {
            Optional<Car> optionalCar = carService.findCarById(carId);
            if (optionalCar.isPresent()){
                session.setAttribute(CURRENT_PAGE, generateUrlWithAttr(TO_ADMIN_EDIT_CAR, CAR_ID_ATTR, carId));
                uploader.uploadCategories(request, false);
                request.setAttribute(CAR, optionalCar.get());
            } else{
                router.setPagePath(generateUrlWithAttr(currentPage, MESSAGE_ATTR, CAR_NOT_EXISTS));
                router.setType(Router.Type.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying find car by id");
            throw new CommandException(e);
        }
        return router;
    }
}
