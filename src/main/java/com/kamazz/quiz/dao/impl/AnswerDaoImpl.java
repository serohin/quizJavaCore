package com.kamazz.quiz.dao.impl;


import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.dao.interfaces.AnswerDao;
import com.kamazz.quiz.model.Answer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AnswerDaoImpl implements AnswerDao {

    @Override
    public List<Answer> getAnswerListbyQuestionId(int questionId, Connection conn) throws NoSuchEntityException, DaoSystemException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT idAnswer,answer,correct,questionId FROM answer WHERE questionId = '" + questionId + "'")) {
            List<Answer> answerList = new ArrayList();
            if (!rs.next()) {
                throw new NoSuchEntityException("No Theme for id='" + questionId + "'");
            } else {
                do {
                    int idAnswer = rs.getInt("idAnswer");
                    String answer = rs.getString("answer");
                    Byte correct = rs.getByte("correct");
                    int idQuestion = rs.getInt("questionId");
                    answerList.add(new Answer(idAnswer, answer, correct, idQuestion));
                } while (rs.next());
            }
            return answerList;
        } catch (SQLException e) {
            throw new DaoSystemException("Some JDBC (or Naming) error.", e);
        }
    }
}
