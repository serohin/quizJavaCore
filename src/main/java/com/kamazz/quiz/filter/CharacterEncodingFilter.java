package com.kamazz.quiz.filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by kamazz on 17.10.16.
 */
public class CharacterEncodingFilter extends BaseFilter {
    @Override
    public void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        String encParam = (String) req.getServletContext().getAttribute("encoding");
        req.setCharacterEncoding(encParam);
        filterChain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String filterParam = filterConfig.getInitParameter("encoding");
        filterConfig.getServletContext().setAttribute("encoding",filterParam);
    }
}
