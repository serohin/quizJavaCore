package com.kamazz.quiz.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.kamazz.quiz.filter.LoginFilter.PARAM_USER;

/**
 * Created by kamazz on 24.11.16.
 */
public class RootFilter extends BaseFilter {
    @Override
    public void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        if (req.getSession() != null && req.getSession().getAttribute(PARAM_USER) != null) {
            req.getRequestDispatcher("WEB-INF/view/section.jsp").forward(req, resp);
        }
    }
}
