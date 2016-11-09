package com.kamazz.quiz.controller;

import com.kamazz.injection.DependencyInjectionServlet;
import com.kamazz.injection.Inject;
import com.kamazz.quiz.dao.datasource.JNDIDatasource;
import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.dao.interfaces.QuizDao;
import com.kamazz.quiz.entity.Quiz;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static java.util.Collections.unmodifiableList;


public class QuizController extends DependencyInjectionServlet {

    public static final String PARAM_THEME_ID = "themeId";
    public static final String ATTRIBUTE_QUIZ_LIST_BY_THEME_ID = "quizListByThemeId";
    public static final String ATTRIBUTE_CURRENT_THEME_ID = "currentThemeId";

    public static final String PAGE_ERROR = "WEB-INF/view/404error.jsp";
    public static final String PAGE_OK = "WEB-INF/view/quiz.jsp";

    @Inject("quizDaoImpl")
    QuizDao quizDao;

    @Inject("jndiDatasource")
    JNDIDatasource jndiDatasource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String strThemeId = req.getParameter(PARAM_THEME_ID);

        if (strThemeId == null) {
            req.getRequestDispatcher(PAGE_ERROR).forward(req, resp);
        } else {

            try {
                Integer.valueOf(strThemeId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                //logger.debug(e);
                req.getRequestDispatcher(PAGE_ERROR).forward(req, resp);
                return;
            }

            final int themeId = Integer.valueOf(strThemeId);
            HttpSession session = req.getSession(true);
            session.setAttribute(ATTRIBUTE_CURRENT_THEME_ID, themeId);

            try (Connection conn = jndiDatasource.getDataSource().getConnection()) {
                conn.setAutoCommit(false);
                List<Quiz> quizList = null;
                try {
                    quizList = quizDao.getQuizListByThemeId(themeId, conn);
                } catch (NoSuchEntityException e) {
                    e.printStackTrace();
                    //logger.debug(e);
                    req.getRequestDispatcher(PAGE_ERROR).forward(req, resp);
                } catch (DaoSystemException e) {
                    conn.rollback();
                    e.printStackTrace();
                    //logger.debug(e);
                }
                conn.commit();
                if (quizList != null) {
                    session.setAttribute(ATTRIBUTE_QUIZ_LIST_BY_THEME_ID, unmodifiableList(quizList));
                    req.getRequestDispatcher(PAGE_OK).forward(req, resp);
                }
            } catch (SQLException e) {
                e.printStackTrace();//убрать
                //logger.debug(e);
            }
        }
    }
}
