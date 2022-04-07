package by.lukyanov.finaltask.controller.listener;

import jakarta.servlet.http.*;

//@WebListener
public class SessionListenerImpl implements HttpSessionListener{


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }
}
