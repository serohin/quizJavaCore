package com.kamazz.quiz.dao.impl;

import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.dao.interfaces.SectionDao;
import com.kamazz.quiz.entity.Section;

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
        try (Statement stmt = conn.createStatement()) {
            List<Section> sectionList = new ArrayList();
            try (ResultSet rs = stmt.executeQuery(SELECT_ALL_SECTION)) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String caption = rs.getString("caption");
                    sectionList.add(new Section(id, caption));
                }
            }
            return sectionList;
        } catch (SQLException e) {
            throw new DaoSystemException("Some JDBC error.", e);
        }
    }

    @Override
    public Section getSectionById(int id, Connection conn) throws DaoSystemException, NoSuchEntityException {
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(SELECT_ALL_SECTION)) {
                if (rs.next()) {
                    int idSection = rs.getInt("id");
                    String caption = rs.getString("caption");
                    return new Section(idSection, caption);
                } else {
                    throw new NoSuchEntityException("No Theme for id='" + id + "'");
                }
            }
        } catch (SQLException e) {
            throw new DaoSystemException("Some JDBC error.", e);
        }
    }
}