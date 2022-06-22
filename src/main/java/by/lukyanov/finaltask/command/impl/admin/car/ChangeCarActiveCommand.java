package by.lukyanov.finaltask.command.impl.admin.car;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.lukyanov.finaltask.command.Message.CAR_STATUS_CHANGED;
import static by.lukyanov.finaltask.command.Message.CAR_STATUS_NOT_CHANGED;
import static by.lukyanov.finaltask.command.PagePath.TO_ADMIN_ALL_CARS;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class ChangeCarActiveCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = CarServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Router router = new Router();
        router.setType(Router.Type.REDIRECT);
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        session.setAttribute(CURRENT_PAGE, TO_ADMIN_ALL_CARS);
        String carId = request.getParameter(CAR_ID);
        String active = request.getParameter(CAR_ACTIVE);
        try {
            if (carService.changeCarActive(carId, active)){
                router.setPagePath(generateUrlWithAttr(currentPage, MESSAGE_ATTR, CAR_STATUS_CHANGED));
            } else {
                router.setPagePath(generateUrlWithAttr(currentPage, MESSAGE_ATTR, CAR_STATUS_NOT_CHANGED));
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying change car active status", e);
            throw new CommandException(e);
        }
        return router;
    }
}
