package by.lukyanov.finaltask.command.impl.admin.car;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.ParameterAttributeName;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.lukyanov.finaltask.command.PagePath.*;

public class ChangeCarActiveCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = new CarServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(TO_ALL_CARS);
        String carId = request.getParameter(ParameterAttributeName.CAR_ID);
        String active = request.getParameter(ParameterAttributeName.CAR_ACTIVE);
        try {
            if (carService.changeCarActive(carId, active)){
                router.setType(Router.Type.REDIRECT);
            } else {
                router.setPagePath(ADMIN_FAIL_PAGE);
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying change car active status", e);
            throw new CommandException(e);
        }
        return router;
    }
}
