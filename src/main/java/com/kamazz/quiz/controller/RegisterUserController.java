package com.kamazz.quiz.controller;

import com.kamazz.injection.DependencyInjectionServlet;
import com.kamazz.injection.Inject;
import com.kamazz.quiz.dao.connection.JNDIConnection;
import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.dao.interfaces.UserDao;
import com.kamazz.quiz.entity.User;
import com.kamazz.quiz.validator.UserValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by kamazz on 13.10.16.
 */
public class RegisterUserController extends DependencyInjectionServlet {

    public static final String PARAM_USER = "user";
    public static final String PARAM_USERNAME = "userName";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_INVITE = "invite";


    public static final String ATTRIBUTE_ERROR_MAP = "errorMap";
    public static final String ATTRIBUTE_INPUT_USERNAME_VALUE = "InputUserNameValue";
    public static final String ATTRIBUTE_INPUT_PASSWORD_VALUE = "InputPasswordValue";
    public static final String ATTRIBUTE_INPUT_INVITE_VALUE = "InputInviteValue";

    public static final String INVITE = "kamazz";
    public static final String KEY_ERROR_MAP_FALSE_INVITE = "falseInvite";
    public static final String KEY_ERROR_MAP_USERNAME = "userName";

    public static final String PAGE_MORE_INFO = "WEB-INF/view/registerUser.jsp";
    public static final String REDIRECT_OK_URL = "./section.do";


    @Inject("userDaoImpl")
    UserDao userDao;

    @Inject("userValidatorImpl")
    UserValidator userValidator;

    @Inject("jndiConnection")
    JNDIConnection jndiConnection;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userName = null;
        String password = null;
        String invite = null;

        if(req.getParameter(PARAM_USERNAME)!= null & req.getParameter(PARAM_USERNAME)!= null  ){
            userName = req.getParameter(PARAM_USERNAME).trim();
            password = req.getParameter(PARAM_PASSWORD).trim();
            invite = req.getParameter(PARAM_INVITE).trim();
        }

        final User user = new User(userName, password);
        final Map<String, String> errorMap = userValidator.validate(user);

        if (!invite.equals(INVITE)) {
            errorMap.put(KEY_ERROR_MAP_FALSE_INVITE, "Введите invite, который находится в моем резюме.");
        }

        if (errorMap.isEmpty()) {
            try (Connection conn = jndiConnection.getConnection()) {
                conn.setAutoCommit(false);
                User tmpUser = null;
                try {
                    try {
                        tmpUser = userDao.getUserByName(user.getUsername(), conn);
                    } catch (NoSuchEntityException e) {/*NOP*/}
                    if (null != tmpUser) {
                        errorMap.put(KEY_ERROR_MAP_USERNAME, "такой логин уже существует!");
                    }
                    if (errorMap.isEmpty()) {
                        User newUser = userDao.insertNewUser(user, conn);
                        HttpSession session = req.getSession(true);
                        session.setAttribute(PARAM_USER, newUser.getUsername());
                        resp.sendRedirect(REDIRECT_OK_URL);
                    } else {
                        req.setAttribute(ATTRIBUTE_ERROR_MAP, errorMap);
                        req.getRequestDispatcher(PAGE_MORE_INFO).forward(req, resp);
                    }
                } catch (DaoSystemException e) {
                    conn.rollback();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();//убрать
                //logger.debug(e);
            }

        } else {
            req.setAttribute(ATTRIBUTE_ERROR_MAP, errorMap);
            req.setAttribute(ATTRIBUTE_INPUT_USERNAME_VALUE, userName);
            req.setAttribute(ATTRIBUTE_INPUT_PASSWORD_VALUE, password);
            req.setAttribute(ATTRIBUTE_INPUT_INVITE_VALUE, invite);
            req.getRequestDispatcher(PAGE_MORE_INFO).forward(req, resp);

        }

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(PAGE_MORE_INFO).forward(req, resp);
    }
}
