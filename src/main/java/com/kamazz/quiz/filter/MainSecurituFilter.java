package com.kamazz.quiz.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.kamazz.quiz.filter.LoginFilter.PARAM_USER;


public class MainSecurituFilter extends BaseFilter {
    public static final String PAGE_ERROR = "WEB-INF/view/index.jsp";

    @Override
    public void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        if (req.getRequestURI().contains("login.do") || req.getRequestURI().contains("registerNewUser.do")) {
            filterChain.doFilter(req, resp);
            return;
        }

        if (req.getSession() != null && req.getSession().getAttribute(PARAM_USER) != null) {
            filterChain.doFilter(req, resp);
        } else {
            req.getRequestDispatcher(PAGE_ERROR).forward(req, resp);

        }
    }

}
