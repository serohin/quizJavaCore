package com.kamazz.quiz.controller;

import com.kamazz.injection.DependencyInjectionServlet;
import com.kamazz.injection.Inject;
import com.kamazz.quiz.dao.datasource.JNDIDatasource;
import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.dao.interfaces.ThemeDao;
import com.kamazz.quiz.entity.Theme;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * Created by kamazz on 27.09.16.
 */
public class ThemeController extends DependencyInjectionServlet {

    public static final String PARAM_SECTION_ID = "sectionId";
    public static final String ATTRIBUTE_THEME_LIST_BY_SECTION_ID = "themeListBySectionId";

    public static final String PAGE_ERROR = "WEB-INF/view/404error.jsp";
    public static final String PAGE_OK = "WEB-INF/view/theme.jsp";

    @Inject("themeDaoImpl")
    ThemeDao themeDao;
    @Inject("jndiDatasource")
    JNDIDatasource jndiDatasource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String strId = req.getParameter(PARAM_SECTION_ID);
        //Если пришли с квизов -ничего
        //Если с Разделов,тогда тянем тему

        if (strId == null) {
            req.getRequestDispatcher(PAGE_ERROR).forward(req, resp);
            return;
        }
        int idValueOff = 0;
        try {
            idValueOff = Integer.valueOf(strId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            //logger.debug(e);
            req.getRequestDispatcher(PAGE_ERROR).forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(true);
        Enumeration<String> namesAttribute = session.getAttributeNames();
        int currentSectionIdInSession = 0;
        while(namesAttribute.hasMoreElements()){
            if(namesAttribute.nextElement().equals(PARAM_SECTION_ID) ){
                currentSectionIdInSession = (int)session.getAttribute(PARAM_SECTION_ID);
            }
        }


        if(idValueOff == currentSectionIdInSession){
            req.getRequestDispatcher(PAGE_OK).forward(req, resp);
            return;
        }else {
            final int id = idValueOff;
            session.setAttribute(PARAM_SECTION_ID,id);
            try (Connection conn = jndiDatasource.getConnection()) {
                conn.setAutoCommit(false);
                List<Theme> themeList = null;
                try {
                    themeList = themeDao.getThemesBySectionId(id, conn);
                } catch (DaoSystemException e) {
                    conn.rollback();
                    e.printStackTrace();
                    //logger.debug(e);
                } catch (NoSuchEntityException e) {
                    e.printStackTrace();
                    //logger.debug(e);
                    req.getRequestDispatcher(PAGE_ERROR).forward(req, resp);
                }
                conn.commit();
                if (null != themeList) {
                    session.setAttribute(ATTRIBUTE_THEME_LIST_BY_SECTION_ID, unmodifiableList(themeList));
                    req.getRequestDispatcher(PAGE_OK).forward(req, resp);
                }
            } catch (SQLException e) {
                e.printStackTrace();//убрать
                //logger.debug(e);
            }


        }




    }
}
