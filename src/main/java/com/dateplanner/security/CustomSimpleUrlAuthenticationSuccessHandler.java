package com.dateplanner.security;

import com.dateplanner.util.Ansi;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;

public class CustomSimpleUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    protected final Log logger = LogFactory.getLog(this.getClass());

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
        System.out.println("\u001B[38;5;" + Ansi.getColor("green") + "m" + "<<< Login Success >>>" + "\u001B[0m");

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        clearAuthenticationAttributes(request);

        String targetUrl;
        if (savedRequest != null) {
            targetUrl = savedRequest.getRedirectUrl();
        } else {
            targetUrl = (String) request.getSession().getAttribute("previousUrl");
            if (targetUrl == null) {
                targetUrl = getDefaultTargetUrl();
            }
        }
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}