package com.kamazz.quiz.dao.impl;

import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.interfaces.SectionDao;
import com.kamazz.quiz.model.Section;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class SectionDaoImpl implements SectionDao {
    public static final String SELECT_ALL_SECTION = "SELECT id,caption FROM section";

    @Override
    public List<Section> getAllSection(Connection conn) throws DaoSystemException {
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(SELECT_ALL_SECTION)) {
            List<Section> sectionList = new ArrayList();
            while (rs.next()) {
                int id = rs.getInt("id");
                String caption = rs.getString("caption");
                sectionList.add(new Section(id, caption));
            }
            return sectionList;
        } catch (SQLException e) {
            throw new DaoSystemException("Some JDBC error.", e);
        }
    }
}