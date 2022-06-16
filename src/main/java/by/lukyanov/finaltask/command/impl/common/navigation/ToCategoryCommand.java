package by.lukyanov.finaltask.command.impl.common.navigation;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.Router;
import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.exception.CommandException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarCategoryServiceImpl;
import by.lukyanov.finaltask.model.service.impl.CarServiceImpl;
import by.lukyanov.finaltask.util.ResultCounter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static by.lukyanov.finaltask.command.PagePath.CAR_CATEGORY_PAGE;
import static by.lukyanov.finaltask.command.PagePath.TO_CAR_CATEGORY_PAGE;
import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class ToCategoryCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final CarServiceImpl carService = CarServiceImpl.getInstance();
    private static final CarCategoryServiceImpl categoryService = CarCategoryServiceImpl.getInstance();
    private static final int POSTS_PER_PAGE = 4;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String catId = request.getParameter(CAR_CATEGORY_ID);
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Router router = new Router(currentPage);
        String currentResultPage = request.getParameter(RESULT_PAGE);
        try {
            Optional<CarCategory> optionalCarCategory = categoryService.findCarCategoryById(catId);
            if(optionalCarCategory.isPresent()){
                CarCategory category = optionalCarCategory.get();
                int pagesCount = ResultCounter.countPages(carService.countAllCarsByCategoryId(category.getId()), POSTS_PER_PAGE);
                request.setAttribute(PAGES_COUNT, pagesCount);
                request.setAttribute(RESULT_PAGE, currentResultPage);
                request.setAttribute(CAR_CATEGORY_TITLE, category.getTitle());
                request.setAttribute(CAR_CATEGORY_ID, catId);
                List<Car> cars = carService.findCarsByCategoryId(catId, currentResultPage, POSTS_PER_PAGE);
                request.setAttribute(LIST, cars);
                router.setPagePath(CAR_CATEGORY_PAGE);
                String path = generateUrlWithAttr(TO_CAR_CATEGORY_PAGE, CAR_CATEGORY_ATTR, catId);
                session.setAttribute(CURRENT_PAGE, generateUrlWithAttr(path, RESULT_PAGE_ATTR, currentResultPage));
            }
        } catch (ServiceException e) {
            logger.error("Command exception trying find all cars", e);
            throw new CommandException(e);
        }
        return router;
    }
}
