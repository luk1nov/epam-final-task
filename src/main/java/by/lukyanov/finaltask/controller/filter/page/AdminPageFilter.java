package by.lukyanov.finaltask.controller.filter.page;

import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.lukyanov.finaltask.command.PagePath.*;
import static by.lukyanov.finaltask.command.ParameterAttributeName.LOGGED_USER;

@WebFilter(filterName = "AdminPageFilter", urlPatterns = {ADMIN_ADD_EDIT_USER, ADMIN_ALL_USERS, UNVERIFIED_USERS,
        ADMIN_SEARCH_USERS_RESULTS},
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class AdminPageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession(false);
        if(session != null && session.getAttribute(LOGGED_USER) != null){
            User user = (User) session.getAttribute(LOGGED_USER);
            if(user.getRole() == UserRole.ADMIN){
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
