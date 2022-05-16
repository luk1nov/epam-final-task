package by.lukyanov.finaltask.controller.filter;

import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.ParameterAttributeName;
import by.lukyanov.finaltask.entity.User;
import by.lukyanov.finaltask.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.lukyanov.finaltask.command.PagePath.MAIN_PAGE;

@WebFilter(filterName = "AccessFilter", urlPatterns = {"/pages/admin/*"},
dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class AccessFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession(false);
        final String redirectPage = httpServletRequest.getRequestURL().toString().replaceAll(httpServletRequest.getRequestURI(), "/" + PagePath.MAIN_PAGE);
        boolean loggedIn = session != null && session.getAttribute(ParameterAttributeName.LOGGED_USER) != null;
        if(loggedIn){
            User user = (User) session.getAttribute(ParameterAttributeName.LOGGED_USER);
            if(user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.MANAGER){
                chain.doFilter(request, response);
            } else {
                logger.debug("logged usual user");
                httpServletResponse.sendRedirect(redirectPage);
            }
        } else {
            logger.debug("not logged");
            httpServletResponse.sendRedirect(redirectPage);
        }
    }
}
