package by.lukyanov.finaltask.controller.filter.page;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.lukyanov.finaltask.command.PagePath.MAIN_PAGE;
import static by.lukyanov.finaltask.command.ParameterAttributeName.LOGGED_USER;

@WebFilter(filterName = "ForNotLoggedPageFilter", urlPatterns = {"/pages/login/*"})
public class ForNotLoggedPageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession(false);
        if(session != null && session.getAttribute(LOGGED_USER) != null){
            httpServletRequest.getRequestDispatcher(MAIN_PAGE)
                    .forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
