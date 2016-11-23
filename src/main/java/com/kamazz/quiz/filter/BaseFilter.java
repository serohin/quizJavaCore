package com.kamazz.quiz.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by kamazz on 25.09.16.
 */
public abstract class BaseFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {/*NOP*/}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        doHttpFilter(req, resp, filterChain);
    }

    @Override
    public void destroy() {/*NOP*/}

    public abstract void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain
            filterChain) throws IOException, ServletException;
}