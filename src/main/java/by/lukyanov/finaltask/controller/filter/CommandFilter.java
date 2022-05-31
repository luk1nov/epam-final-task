package by.lukyanov.finaltask.controller.filter;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.CommandType;
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
import java.util.EnumSet;
import java.util.List;

import static by.lukyanov.finaltask.command.PagePath.MAIN_PAGE;
import static by.lukyanov.finaltask.command.ParameterAttributeName.COMMAND;
import static by.lukyanov.finaltask.command.ParameterAttributeName.LOGGED_USER;

@WebFilter(filterName = "CommandFilter", urlPatterns = {"/controller"},
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class CommandFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();
    private static List<Command> notAccessibleCommands;

    public CommandFilter() {
        EnumSet<CommandType> notAccessibleCommandTypes = EnumSet.range(CommandType.ADMIN_TO_ALL_USERS, CommandType.ADMIN_EDIT_CAR_CATEGORY);
        notAccessibleCommands = notAccessibleCommandTypes.stream().map(CommandType::getCommand).toList();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession(false);
        String commandParameter = request.getParameter(COMMAND);
        Command command = CommandType.of(commandParameter);

        boolean loggedIn = (session != null && session.getAttribute(LOGGED_USER) != null);

        if (notAccessibleCommands.contains(command)){
            if(loggedIn){
                User user = (User) session.getAttribute(ParameterAttributeName.LOGGED_USER);
                if(user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.MANAGER){
                    chain.doFilter(request, response);
                } else {
                    logger.debug("user not admin");
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + MAIN_PAGE);
                }
            } else {
                logger.debug("user not logged in");
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + MAIN_PAGE);
            }
        } else {
            chain.doFilter(request, response);
        }

    }
}
