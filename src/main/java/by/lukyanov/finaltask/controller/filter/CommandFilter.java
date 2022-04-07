package by.lukyanov.finaltask.controller.filter;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.CommandType;
import by.lukyanov.finaltask.command.PagePath;
import by.lukyanov.finaltask.command.ParameterAndAttribute;
import by.lukyanov.finaltask.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

import static by.lukyanov.finaltask.command.ParameterAndAttribute.LOGGED_USER;

@WebFilter(filterName = "CommandFilter", urlPatterns = {"/controller"},
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class CommandFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.debug("command filter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession(false);
        Command command = CommandType.define(httpServletRequest);
        EnumSet<CommandType> notAccessibleCommandTypes = EnumSet.range(CommandType.TO_ADMIN_ALL_USERS_PAGE, CommandType.TO_ADMIN_ADD_NEW_CAR_PAGE);
        final String redirectPage = httpServletRequest.getRequestURL().toString().replaceAll(httpServletRequest.getRequestURI(), "/" + PagePath.MAIN_PAGE);
        List<Command> notAccessibleCommands = notAccessibleCommandTypes.stream().map(CommandType::getCommand).toList();

        boolean loggedIn = (session != null && session.getAttribute(LOGGED_USER) != null);

        if (notAccessibleCommands.contains(command)){
            logger.debug("admin command");
            if(loggedIn){
                User user = (User) session.getAttribute(ParameterAndAttribute.LOGGED_USER);
                if(user.getRole() == User.Role.ADMIN || user.getRole() == User.Role.MANAGER){
                    logger.debug("user admin");
                    chain.doFilter(request, response);
                } else {
                    logger.debug("user not admin");
                    httpServletResponse.sendRedirect(redirectPage);
                }
            } else {
                logger.debug("user not logged in");
                httpServletResponse.sendRedirect(redirectPage);
            }
        } else {
            logger.debug("not admin command");
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
    }
}
