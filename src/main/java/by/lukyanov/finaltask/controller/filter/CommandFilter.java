package by.lukyanov.finaltask.controller.filter;

import by.lukyanov.finaltask.command.Command;
import by.lukyanov.finaltask.command.CommandType;
import by.lukyanov.finaltask.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

import static by.lukyanov.finaltask.command.CommandType.*;
import static by.lukyanov.finaltask.command.PagePath.MAIN_PAGE;
import static by.lukyanov.finaltask.command.ParameterAttributeName.COMMAND;
import static by.lukyanov.finaltask.command.ParameterAttributeName.LOGGED_USER;

@WebFilter(filterName = "CommandFilter", urlPatterns = {"/controller"},
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class CommandFilter implements Filter {
    private List<Command> adminCommands;
    private List<Command> managerCommands;
    private List<Command> commonCommands;
    private List<Command> onlyGuestCommands;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        commonCommands = getCommandList(DEFAULT, FINISH_RENT);
        managerCommands = getCommandList(DEFAULT, ADMIN_EDIT_CAR_CATEGORY);
        adminCommands = getCommandList(DEFAULT, ADMIN_DECLINE_USER);
        onlyGuestCommands = getCommandList(SIGN_UP, TO_CAR_CATEGORY_PAGE);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean access;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession(false);
        String commandParameter = request.getParameter(COMMAND);
        Command command = CommandType.of(commandParameter);
        if(session != null && session.getAttribute(LOGGED_USER) != null){
            User user = (User) session.getAttribute(LOGGED_USER);
            access = switch (user.getRole()) {
                case USER -> commonCommands.contains(command);
                case MANAGER -> managerCommands.contains(command);
                case ADMIN -> adminCommands.contains(command);
                default -> false;
            };
        } else {
            access = onlyGuestCommands.contains(command);
        }

        if(access){
            chain.doFilter(request, response);
        } else {
            httpServletRequest.getRequestDispatcher(MAIN_PAGE)
                    .forward(request, response);
        }
    }


    private List<Command> getCommandList(CommandType firstCommandType, CommandType secondCommandType){
        EnumSet<CommandType> commandTypes = EnumSet.range(firstCommandType, secondCommandType);
        return commandTypes.stream().map(CommandType::getCommand).toList();
    }
}
