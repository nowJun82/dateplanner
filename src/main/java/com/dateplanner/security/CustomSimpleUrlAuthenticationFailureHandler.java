package com.dateplanner.security;

import com.dateplanner.util.Ansi;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

public class CustomSimpleUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("\u001B[38;5;" + Ansi.getColor("red") + "m" + "<<< Login Fail >>>" + "\u001B[0m");
        System.out.println("\u001B[38;5;" + Ansi.getColor("red") + "m" + "<<< request: %s >>>".formatted(request) + "\u001B[0m");
        System.out.println("\u001B[38;5;" + Ansi.getColor("red") + "m" + "<<< response: %s >>>".formatted(response) + "\u001B[0m");
        System.out.println("\u001B[38;5;" + Ansi.getColor("red") + "m" + "<<< exception: %s >>>".formatted(exception) + "\u001B[0m");

        super.onAuthenticationFailure(request, response, exception);
    }
}