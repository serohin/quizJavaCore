package com.kamazz.quiz.dao.impl;


import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.dao.interfaces.AnswerDao;
import com.kamazz.quiz.dao.interfaces.QuestionDao;
import com.kamazz.quiz.entity.Answer;
import com.kamazz.quiz.entity.Question;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoImpl implements QuestionDao {
    AnswerDao answerDao = new AnswerDaoImpl();

    @Override
    public List<Question> getQuestionListByQuizId(int quizId, Connection conn) throws DaoSystemException, NoSuchEntityException {
        try (Statement stmt = conn.createStatement()){
            List<Question> questionList = new ArrayList<>();
            try(ResultSet rs = stmt.executeQuery("SELECT questionId,caption,question,quizId FROM question WHERE quizId = '" + quizId + "'")) {
                if (!rs.next()) {
                    throw new NoSuchEntityException("No Theme for id='" + quizId + "'");
                } else {
                    do {
                        int questionId = rs.getInt("questionId");
                        String caption = rs.getString("caption");
                        String question = rs.getString("question");
                        int idQuiz = rs.getInt("quizId");
                        List<Answer> answerList = answerDao.getAnswerListbyQuestionId(questionId, conn);
                        questionList.add(new Question(questionId, caption, question, idQuiz, answerList, new Answer()));
                    } while (rs.next());
                }
                return questionList;
            }
        } catch (SQLException e) {
            throw new DaoSystemException("Some JDBC error.", e);
        }
    }
}
