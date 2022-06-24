package by.lukyanov.finaltask.controller.filter.page;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

import static by.lukyanov.finaltask.command.PagePath.MAIN_PAGE;

@WebFilter(filterName = "TemplatePartFilter", urlPatterns = {"/pages/admin/template-parts/*", "/pages/components/*"},
dispatcherTypes = DispatcherType.REQUEST)
public class TemplatePartFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        httpServletRequest.getRequestDispatcher(MAIN_PAGE)
                .forward(request, response);
    }
}
