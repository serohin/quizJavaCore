package com.kamazz.quiz.controller;

import com.kamazz.injection.DependencyInjectionServlet;
import com.kamazz.injection.Inject;
import com.kamazz.quiz.dao.datasource.JNDIDatasource;
import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.dao.interfaces.QuizDao;
import com.kamazz.quiz.model.Quiz;
import com.kamazz.quiz.validator.RequestParameterValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableList;


public class QuizController extends DependencyInjectionServlet {

    public static final String PARAM_THEME_ID = "themeId";
    public static final String ATTRIBUTE_MODEL_TO_VIEW = "quizListByThemeId";
    public static final String ATTRIBUTE_CURRENT_THEME_ID = "currentThemeId";

    public static final String PAGE_OK = "WEB-INF/view/quiz.jsp";

    @Inject("quizDaoImpl")
    QuizDao quizDao;

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
        Map<String, String> errorMap = paramValidator.validate(req.getParameter(PARAM_THEME_ID));

        if (errorMap.isEmpty()) {
            String strThemeId = req.getParameter(PARAM_THEME_ID);
            final int themeId = Integer.valueOf(strThemeId);
            try (Connection conn = jndiDatasource.getDataSource().getConnection()) {
                conn.setAutoCommit(false);
                try {
                    List<Quiz> quizList = quizDao.getQuizListByThemeId(themeId, conn);
                    HttpSession session = req.getSession(true);
                    session.setAttribute(ATTRIBUTE_CURRENT_THEME_ID, themeId);
                    session.setAttribute(ATTRIBUTE_MODEL_TO_VIEW, unmodifiableList(quizList));
                    // OK
                    req.getRequestDispatcher(PAGE_OK).forward(req, resp);
                } catch (NoSuchEntityException e) {
                    //logger.debug(e);
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
        // FAIL
        req.getRequestDispatcher(SectionController.PAGE_ERROR).forward(req, resp);
    }
}
