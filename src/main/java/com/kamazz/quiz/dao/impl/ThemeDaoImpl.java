package com.kamazz.quiz.dao.impl;


import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.dao.interfaces.ThemeDao;
import com.kamazz.quiz.entity.Theme;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ThemeDaoImpl implements ThemeDao {


    @Override
    public List<Theme> getThemesBySectionId(int sectionId, Connection conn) throws NoSuchEntityException, DaoSystemException {
        try (Statement stmt = conn.createStatement()) {
            List<Theme> themeList = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery("SELECT id,caption,sectionId FROM theme WHERE sectionId = '" + sectionId + "'")) {
                if (!rs.next()) {
                    throw new NoSuchEntityException("No Theme for id='" + sectionId + "'");
                } else {
                    do {
                        int id = rs.getInt("id");
                        String caption = rs.getString("caption");
                        int idSection = rs.getInt("sectionId");
                        themeList.add(new Theme(id, caption, idSection));
                    } while (rs.next());
                }
            }
            return themeList;
        } catch (SQLException e) {
            throw new DaoSystemException("Some JDBC error.", e);
        }
    }

}
