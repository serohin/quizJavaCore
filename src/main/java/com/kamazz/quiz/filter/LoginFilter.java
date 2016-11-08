package com.kamazz.quiz.filter;


import com.kamazz.injection.DependencyInjectionFilter;
import com.kamazz.injection.Inject;
import com.kamazz.quiz.dao.connection.JNDIConnection;
import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.dao.interfaces.UserDao;
import com.kamazz.quiz.entity.User;
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

    public static final String PAGE_LOGIN = "WEB-INF/view/index.jsp";
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

    @Inject("jndiConnection")
    JNDIConnection jndiConnection;

    @Override
    public void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException, ServletException {

        HttpSession session = req.getSession(false);
        String userInSession = (String) session.getAttribute(PARAM_USER);
        if(null != userInSession){
            req.getRequestDispatcher(PAGE_LOGIN).forward(req, resp);
            return;
        }

        req.setCharacterEncoding("UTF-8");
        String userName = null;
        String password = null;
        if (req.getParameter(PARAM_USERNAME) != null & req.getParameter(PARAM_USERNAME) != null) {
            userName = req.getParameter(PARAM_USERNAME).trim();
            password = req.getParameter(PARAM_PASSWORD).trim();
        }



        final User user = new User(userName, password);
        Map<String, String> errorMap = userValidator.validate(user);

        if (errorMap.isEmpty() & null == userInSession) {
            User newUser = null;
            try (Connection conn = jndiConnection.getConnection()) {
                conn.setAutoCommit(false);
                try {
                    newUser = userDao.getUserByName(userName, conn);
                } catch (NoSuchEntityException e) {
                    errorMap.put(KEY_ERROR_MAP_NO_ENTITY, "нет такого пользователя");
                } catch (DaoSystemException e) {
                    conn.rollback();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();//убрать
                //logger.debug(e);
            }
            if (null != newUser && newUser.getPassword().equals(password)) {
                session.setAttribute(PARAM_USER, newUser.getUsername());
                resp.sendRedirect(REDIRECT_OK_URL);
                return;
            }
        }

        if (!errorMap.isEmpty()) {
            req.setAttribute(ATTRIBUTE_ERROR_MAP, errorMap);
            req.getRequestDispatcher(PAGE_LOGIN).forward(req, resp);
        }
    }
}
