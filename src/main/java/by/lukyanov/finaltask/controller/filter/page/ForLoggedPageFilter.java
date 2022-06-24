package by.lukyanov.finaltask.controller.filter.page;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.lukyanov.finaltask.command.PagePath.CAR_PAGE;
import static by.lukyanov.finaltask.command.PagePath.SIGN_IN_PAGE;
import static by.lukyanov.finaltask.command.ParameterAttributeName.LOGGED_USER;

@WebFilter(filterName = "ForLoggedPageFilter", urlPatterns = {"/pages/order/*", "/pages/user/*", CAR_PAGE},
dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class ForLoggedPageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession(false);
        if(session == null || session.getAttribute(LOGGED_USER) == null){
            httpServletRequest.getRequestDispatcher(SIGN_IN_PAGE)
                    .forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
