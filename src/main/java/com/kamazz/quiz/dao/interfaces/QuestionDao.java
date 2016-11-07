package com.kamazz.quiz.dao.interfaces;


import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.entity.Question;

import java.sql.Connection;
import java.util.List;

public interface QuestionDao{
        List<Question> getQuestionListByQuizId(int quizId,Connection conn) throws DaoSystemException, NoSuchEntityException;
}
