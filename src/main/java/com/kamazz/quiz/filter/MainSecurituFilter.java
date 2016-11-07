package com.kamazz.quiz.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by kamazz on 10.10.16.
 */
public class MainSecurituFilter extends BaseFilter {
    public static final String PAGE_LOGIN = "WEB-INF/view/index.jsp";
    public static final String PARAM_USER = "user";//???

    @Override
    public void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException, ServletException {


            if (req.getRequestURI().contains("login.do") || req.getRequestURI().contains("registerNewUser.do")) {
                filterChain.doFilter(req, resp);
                return;
            }
                                                                               //???
            if (req.getSession() != null && req.getSession().getAttribute(PARAM_USER) != null) {
                // авторизирован: сессия существует и содержит пользователя
                // просто пропускаем запрос дальше
                filterChain.doFilter(req, resp);
            } else {
                // неавторизирован:
                // 1. делаем "внешний redirect"  на login.jsp
                // res.sendRedirect(PAGE_LOGIN);
               req.getRequestDispatcher(PAGE_LOGIN).forward(req, resp);

            }
    }

}
