package com.kamazz.quiz.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class LogoutFilter extends BaseFilter {
    public static final String PAGE_OK = "WEB-INF/view/index.jsp";

    @Override
    public void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException, ServletException {

        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
            req.getRequestDispatcher(PAGE_OK).forward(req, resp);
        }
    }
}
