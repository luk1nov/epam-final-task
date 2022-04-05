package by.lukanov.final_task.controller.filter;

import by.lukanov.final_task.command.PagePath;
import by.lukanov.final_task.command.ParameterAndAttribute;
import by.lukanov.final_task.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter(filterName = "AccessFilter", urlPatterns = {"/pages/admin/*"},
dispatcherTypes = {DispatcherType.FORWARD})
public class AccessFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession(false);

        boolean loggedIn = session != null && session.getAttribute(ParameterAndAttribute.LOGGED_USER) != null;

        if(loggedIn){
            User user = (User) session.getAttribute(ParameterAndAttribute.LOGGED_USER);
            if(user.getRole() == User.Role.ADMIN || user.getRole() == User.Role.MANAGER){
                chain.doFilter(request, response);
            } else {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + PagePath.MAIN_PAGE);
            }
        } else {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + PagePath.MAIN_PAGE);
        }
    }

    public void destroy() {
    }
}
