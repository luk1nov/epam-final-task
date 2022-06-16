package by.lukyanov.finaltask.command.impl.util;

import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.service.impl.CarCategoryServiceImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static by.lukyanov.finaltask.command.ParameterAttributeName.CONTEXT_CATEGORIES;

public final class ContextCategoryUploader {
    private static final CarCategoryServiceImpl categoryService = CarCategoryServiceImpl.getInstance();
    private static final ContextCategoryUploader instance = new ContextCategoryUploader();

    private ContextCategoryUploader() {
    }

    public static ContextCategoryUploader getInstance(){
        return instance;
    }

    public void uploadCategories(HttpServletRequest request, boolean forcibly) throws ServiceException {
        ServletContext context = request.getServletContext();
        List<CarCategory> currentCategories = (List<CarCategory>) context.getAttribute(CONTEXT_CATEGORIES);
        if (forcibly || currentCategories == null){
            List<CarCategory> categoryList = categoryService.findAllCarCategories();
            context.setAttribute(CONTEXT_CATEGORIES, categoryList);
        }
    }
}
