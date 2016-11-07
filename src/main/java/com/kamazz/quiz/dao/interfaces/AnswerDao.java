package com.kamazz.quiz.dao.interfaces;

import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.entity.Answer;

import java.sql.Connection;
import java.util.List;


public interface AnswerDao {
    List<Answer> getAnswerListbyQuestionId(int questionId, Connection conn) throws NoSuchEntityException, DaoSystemException;
}
