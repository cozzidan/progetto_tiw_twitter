package com.elis.twitter.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/LoggedFilter")
public class LoggedFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        // user logged in ?
        HttpSession session = httpRequest.getSession();
        boolean isLoggedIn = (session != null && session.getAttribute("loggedInUser") != null);

        if (isLoggedIn) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpRequest.getSession().setAttribute("filterMessage", "Devi prima effettuare il login.");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/Login");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
