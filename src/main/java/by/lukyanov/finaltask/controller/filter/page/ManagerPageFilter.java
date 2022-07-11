package by.lukyanov.finaltask.controller.filter.page;

import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.lukyanov.finaltask.command.PagePath.ADMIN_USER_INFO;
import static by.lukyanov.finaltask.command.PagePath.MAIN_PAGE;
import static by.lukyanov.finaltask.command.ParameterAttributeName.LOGGED_USER;

/**
 * Filter for accessing only for manager and admin pages.
 */
@WebFilter(filterName = "ManagerPageFilter", urlPatterns = {"/pages/admin/cars/*", "/pages/admin/orders/*",
        ADMIN_USER_INFO, "/pages/admin/template-parts/*"},
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class ManagerPageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession(false);
        if(session != null && session.getAttribute(LOGGED_USER) != null){
            User user = (User) session.getAttribute(LOGGED_USER);
            if(user.getRole() != UserRole.USER){
                chain.doFilter(request, response);
            } else {
                httpServletRequest.getRequestDispatcher(MAIN_PAGE)
                        .forward(request, response);
            }
        } else {
            httpServletRequest.getRequestDispatcher(MAIN_PAGE)
                    .forward(request, response);
        }
    }
}
