package com.kamazz.quiz.dao.impl;

import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.dao.interfaces.QuizDao;
import com.kamazz.quiz.entity.Quiz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class QuizDaoImpl implements QuizDao {
    @Override
    public List<Quiz> getQuizListByThemeId(int themeId, Connection conn) throws NoSuchEntityException, DaoSystemException {
        try (Statement stmt = conn.createStatement()) {
            List<Quiz> quizList = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery("SELECT id,caption,themeId FROM quiz WHERE themeId = '" + themeId + "'")) {
                if (!rs.next()) {
                    throw new NoSuchEntityException("No Theme for id='" + themeId + "'");
                } else {
                    do {
                        int id = rs.getInt("id");
                        String caption = rs.getString("caption");
                        int idTheme = rs.getInt("themeId");
                        quizList.add(new Quiz(id, caption, idTheme));
                    } while (rs.next());
                }
            }
            return quizList;
        } catch (SQLException e) {
            throw new DaoSystemException("Some JDBC error.", e);
        }
    }
}
