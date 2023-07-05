package com.elis.twitter.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/ThreadFilter")
public class ThreadFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        // thread in session ?
        HttpSession session = httpRequest.getSession();
        boolean inSession = (session != null && session.getAttribute("thread") != null);

        if (inSession) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpRequest.getSession().setAttribute("filterMessage", "Devi prima selezionare un thread.");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/Dashboard");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
