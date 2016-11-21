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
    public static final String ATTRIBUTE_SECTION_LIST = "sectionList";

    public static final String PAGE_OK = "WEB-INF/view/section.jsp";
    public static final String PAGE_GET_ERROR = "WEB-INF/view/getResponseError.jsp";

    @Inject("jndiDatasource")
    JNDIDatasource jndiDatasource;

    @Inject("sectionDaoImpl")
    private SectionDao sectionDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        List<Section> sectionList = (List<Section>) session.getAttribute(ATTRIBUTE_SECTION_LIST);
        if(sectionList == null){
            try (Connection conn = jndiDatasource.getDataSource().getConnection()) {
                conn.setAutoCommit(false);

                List<Section> allSectionList = null;
                try {
                    allSectionList = sectionDao.getAllSection(conn);
                } catch (DaoSystemException e) {
                    conn.rollback();
                }
                conn.commit();
                if (allSectionList != null) {
                    session.setAttribute(ATTRIBUTE_SECTION_LIST, unmodifiableList(allSectionList));
                    req.getRequestDispatcher(PAGE_OK).forward(req, resp);
                }

            } catch (SQLException e) {
                e.printStackTrace();//убрать
                //logger.debug(e);
            }
        }else{
            req.getRequestDispatcher(PAGE_GET_ERROR).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(PAGE_OK).forward(req, resp);
    }
}
