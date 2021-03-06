package com.kamazz.quiz.controller;

import com.kamazz.injection.DependencyInjectionServlet;
import com.kamazz.injection.Inject;
import com.kamazz.quiz.dao.datasource.JNDIDatasource;
import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.interfaces.SectionDao;
import com.kamazz.quiz.model.Section;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static java.util.Collections.unmodifiableList;


public class SectionController extends DependencyInjectionServlet {
    public static final String ATTRIBUTE_MODEL_TO_VIEW = "sectionList";

    public static final String PAGE_OK = "WEB-INF/view/section.jsp";
    public static final String PAGE_ERROR = "WEB-INF/view/getResponseError.jsp";

    @Inject("jndiDatasource")
    JNDIDatasource jndiDatasource;

    @Inject("sectionDaoImpl")
    private SectionDao sectionDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        List<Section> sectionList = (List<Section>) session.getAttribute(ATTRIBUTE_MODEL_TO_VIEW);
        if (sectionList == null) {
            try (Connection conn = jndiDatasource.getDataSource().getConnection()) {
                conn.setAutoCommit(false);
                try {
                    List<Section> allSectionList = sectionDao.getAllSection(conn);
                    session.setAttribute(ATTRIBUTE_MODEL_TO_VIEW, unmodifiableList(allSectionList));
                    // OK
                    req.getRequestDispatcher(PAGE_OK).forward(req, resp);
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
        req.getRequestDispatcher(PAGE_ERROR).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(PAGE_OK).forward(req, resp);
    }
}
