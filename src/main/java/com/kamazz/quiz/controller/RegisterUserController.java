package com.kamazz.quiz.controller;

import com.kamazz.injection.DependencyInjectionServlet;
import com.kamazz.injection.Inject;
import com.kamazz.quiz.dao.datasource.JNDIDatasource;
import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.dao.interfaces.UserDao;
import com.kamazz.quiz.filter.LoginFilter;
import com.kamazz.quiz.model.User;
import com.kamazz.quiz.validator.RequestParameterValidator;
import com.kamazz.quiz.validator.UserValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;




public class RegisterUserController extends DependencyInjectionServlet {
    public static final String PARAM_INVITE = "invite";
    public static final String ATTRIBUTE_ERROR_MAP = "errorMap";
    public static final String ATTRIBUTE_INPUT_USERNAME_VALUE = "InputUserNameValue";
    public static final String ATTRIBUTE_INPUT_PASSWORD_VALUE = "InputPasswordValue";

    public static final String INVITE = "kamazz";
    public static final String KEY_ERROR_MAP_FALSE_INVITE = "falseInvite";
    public static final String KEY_ERROR_MAP_USERNAME = "userName";

    public static final String PAGE_MORE_INFO = "WEB-INF/view/registerUser.jsp";
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(SectionController.PAGE_ERROR).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter(LoginFilter.PARAM_USERNAME);
        String password = req.getParameter(LoginFilter.PARAM_PASSWORD);
        final User userRegister = new User(userName, password);
        Map<String, String> errorMapUserRegister = userValidator.validate(userRegister);

        Map<String,String> errorMapInvite = paramValidator.validate(req.getParameter(PARAM_INVITE));
        if(errorMapInvite.isEmpty()){
            String invite = req.getParameter(PARAM_INVITE);
            if (!invite.equals(INVITE)) {
                errorMapUserRegister.put(KEY_ERROR_MAP_FALSE_INVITE, "Введите invite, который находится в моем резюме.");
            }
        }
        if (errorMapUserRegister.isEmpty()) {
            try (Connection conn = jndiDatasource.getDataSource().getConnection()) {
                conn.setAutoCommit(false);
                try {
                    try {
                        userDao.getUserByName(userRegister.getUsername(), conn);
                        errorMapUserRegister.put(KEY_ERROR_MAP_USERNAME, "такой логин уже существует!");
                        req.setAttribute(ATTRIBUTE_ERROR_MAP, errorMapUserRegister);
                        req.getRequestDispatcher(PAGE_MORE_INFO).forward(req, resp);
                    } catch (NoSuchEntityException e) {/*NOP*/}

                    if (errorMapUserRegister.isEmpty()) {
                        User newUser = userDao.insertNewUser(userRegister, conn);
                        HttpSession session = req.getSession(true);
                        session.setAttribute(LoginFilter.PARAM_USER, newUser.getUsername());
                        resp.sendRedirect(REDIRECT_OK_URL);
                    }
                } catch (DaoSystemException e) {
                    conn.rollback();
                    //logger.debug(e);
                }
                conn.commit();
                return;
            } catch (SQLException e) {
                //logger.debug(e);
            }
        }
        req.setAttribute(ATTRIBUTE_ERROR_MAP, errorMapUserRegister);
        req.setAttribute(ATTRIBUTE_INPUT_USERNAME_VALUE, userName);
        req.setAttribute(ATTRIBUTE_INPUT_PASSWORD_VALUE, password);
        req.getRequestDispatcher(PAGE_MORE_INFO).forward(req, resp);

    }
}
