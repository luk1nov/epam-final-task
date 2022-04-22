package by.lukyanov.finaltask.controller.listener;

import by.lukyanov.finaltask.command.PagePath;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.*;

import static by.lukyanov.finaltask.command.ParameterAndAttribute.*;

@WebListener
public class SessionListenerImpl implements HttpSessionListener{
    private static final String DEFAULT_LOCALE = "en_US";
    private static final String DEFAULT_LANGUAGE = "EN";

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("session created");
        HttpSession session = se.getSession();
        session.setAttribute(CURRENT_PAGE, PagePath.MAIN_PAGE);
        session.setAttribute(LOCALE, DEFAULT_LOCALE);
        session.setAttribute(LANGUAGE, DEFAULT_LANGUAGE);
    }
}
