package com.dateplanner.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class Rq {
    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;
    private final HttpSession session;
    private final User user;

    public Rq(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpSession session) {
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
        this.session = session;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            this.user = (User) authentication.getPrincipal();
        } else {
            this.user = null;
        }
    }

    public boolean isLogin() {
        return user != null;
    }

    public boolean isLogout() {
        return !isLogin();
    }
}