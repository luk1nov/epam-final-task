package by.lukyanov.final_task.controller.filter;

import by.lukyanov.final_task.command.PagePath;
import by.lukyanov.final_task.command.ParameterAndAttribute;
import by.lukyanov.final_task.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter(filterName = "AccessFilter", urlPatterns = {"/pages/admin/*"},
dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class AccessFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.debug("access filter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession(false);

        boolean loggedIn = session != null && session.getAttribute(ParameterAndAttribute.LOGGED_USER) != null;
        final String redirectPage = httpServletRequest.getRequestURL().toString().replaceAll(httpServletRequest.getRequestURI(), "/" + PagePath.MAIN_PAGE);
        if(loggedIn){
            logger.debug("logged");
            User user = (User) session.getAttribute(ParameterAndAttribute.LOGGED_USER);
            if(user.getRole() == User.Role.ADMIN || user.getRole() == User.Role.MANAGER){
                logger.debug("logged admin or manager");
                chain.doFilter(request, response);
            } else {
                logger.debug("logged user");
                httpServletResponse.sendRedirect(redirectPage);
            }
        } else {
            logger.debug("not logged");
            httpServletResponse.sendRedirect(redirectPage);
        }
    }

    public void destroy() {
    }

}
