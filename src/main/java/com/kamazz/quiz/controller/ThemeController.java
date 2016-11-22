package com.kamazz.quiz.controller;

import com.kamazz.injection.DependencyInjectionServlet;
import com.kamazz.injection.Inject;
import com.kamazz.quiz.dao.datasource.JNDIDatasource;
import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.dao.interfaces.ThemeDao;
import com.kamazz.quiz.model.Theme;
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

/**
 * Created by kamazz on 27.09.16.
 */
public class ThemeController extends DependencyInjectionServlet {

    public static final String PARAM_SECTION_ID = "sectionId";
    public static final String ATTRIBUTE_THEME_LIST_BY_SECTION_ID = "themeListBySectionId";

    public static final String PAGE_GET_ERROR = "WEB-INF/view/getResponseError.jsp";
    public static final String PAGE_ERROR = "WEB-INF/view/404error.jsp";//delete???
    public static final String PAGE_OK = "WEB-INF/view/theme.jsp";

    @Inject("themeDaoImpl")
    ThemeDao themeDao;

    @Inject("jndiDatasource")
    JNDIDatasource jndiDatasource;

    @Inject("requestParameterValidatorImpl")
    RequestParameterValidator paramValidator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(PAGE_GET_ERROR).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> errorMap = paramValidator.validateId(req.getParameter(PARAM_SECTION_ID));

        if (errorMap.isEmpty()) {
            String strId = req.getParameter(PARAM_SECTION_ID);
            final int id = Integer.valueOf(strId);
            try (Connection conn = jndiDatasource.getDataSource().getConnection()) {
                conn.setAutoCommit(false);
                try {
                    List<Theme> themeList = themeDao.getThemesBySectionId(id, conn);
                    HttpSession session = req.getSession(true);
                    session.setAttribute(PARAM_SECTION_ID,id);
                    session.setAttribute(ATTRIBUTE_THEME_LIST_BY_SECTION_ID, unmodifiableList(themeList));
                    // OK
                    req.getRequestDispatcher(PAGE_OK).forward(req, resp);
                } catch (DaoSystemException e) {
                    conn.rollback();
                    //logger.debug(e);
                } catch (NoSuchEntityException e) {
                    //req.getRequestDispatcher(PAGE_ERROR).forward(req, resp);
                    //logger.debug(e);
                }
                conn.commit();
                return;
            } catch (SQLException e) {
                e.printStackTrace();
                //logger.debug(e);
            }
        }
        // FAIL
        req.getRequestDispatcher(PAGE_OK).forward(req, resp);
    }
}
