package com.kamazz.quiz.filter;


import com.kamazz.injection.DependencyInjectionFilter;
import com.kamazz.injection.Inject;
import com.kamazz.quiz.dao.datasource.JNDIDatasource;
import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.dao.interfaces.UserDao;
import com.kamazz.quiz.model.User;
import com.kamazz.quiz.validator.RequestParameterValidator;
import com.kamazz.quiz.validator.UserValidator;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class LoginFilter extends DependencyInjectionFilter {

    public static final String PAGE_ERROR = "WEB-INF/view/index.jsp";

    public static final String PARAM_USER = "user";
    public static final String PARAM_USERNAME = "userName";
    public static final String PARAM_PASSWORD = "password";

    public static final String ATTRIBUTE_ERROR_MAP = "errorMap";
    public static final String KEY_ERROR_MAP_NO_ENTITY = "noEntity";
    public static final String REDIRECT_OK_URL = "./section.do";

    @Inject("userDaoImpl")
    UserDao userDao;

    @Inject("userValidatorImpl")
    UserValidator userValidator;

    @Inject("requestParameterValidatorImpl")
    RequestParameterValidator paramValidator;

    @Inject("jndiDatasource")
    JNDIDatasource jndiDatasource;

    @Override
    public void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        Map<String, String> errorMapUserInSession = paramValidator.validate((String) session.getAttribute(PARAM_USER));

        if (errorMapUserInSession.isEmpty()) {
            req.getRequestDispatcher(PAGE_ERROR).forward(req, resp);
            return;
        }
        req.setCharacterEncoding("UTF-8");
        String userName = req.getParameter(PARAM_USERNAME);
        String password = req.getParameter(PARAM_PASSWORD);
        final User user = new User(userName, password);
        Map<String, String> errorMapUser = userValidator.validate(user);
        User newUser = null;

        if (errorMapUser.isEmpty() & !errorMapUserInSession.isEmpty()) {
            try (Connection conn = jndiDatasource.getDataSource().getConnection()) {
                conn.setAutoCommit(false);
                try {
                    newUser = userDao.getUserByName(userName, conn);
                    if (!newUser.getPassword().equals(password)) {
                        errorMapUser.put(KEY_ERROR_MAP_NO_ENTITY, "нет такого пользователя");
                    }
                } catch (NoSuchEntityException e) {
                    errorMapUser.put(KEY_ERROR_MAP_NO_ENTITY, "нет такого пользователя");
                    //logger.debug(e);
                } catch (DaoSystemException e) {
                    conn.rollback();
                    //logger.debug(e);
                }
                conn.commit();
            } catch (SQLException e) {
                //logger.debug(e);
            }
        }

        if (errorMapUser.isEmpty()) {
            session.setAttribute(PARAM_USER, newUser.getUsername());
            resp.sendRedirect(REDIRECT_OK_URL);
        } else {
            req.setAttribute(ATTRIBUTE_ERROR_MAP, errorMapUser);
            req.getRequestDispatcher(PAGE_ERROR).forward(req, resp);
        }
    }
}
