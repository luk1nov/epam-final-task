package by.lukyanov.finaltask.command.impl.admin.car;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.ParameterAndAttribute;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.lukyanov.finaltask.command.PagePath.*;


public class DeleteCarCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = new CarServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String carId = request.getParameter(ParameterAndAttribute.CAR_ID);
        try {
            if (carService.deleteCarById(carId)){
                router.setType(Router.Type.REDIRECT);
                router.setPagePath(SUCCESS_PAGE);
            } else {
                router.setPagePath(FAIL_PAGE);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying delete car", e);
            throw new CommandException(e);
        }
        return router;
    }
}
