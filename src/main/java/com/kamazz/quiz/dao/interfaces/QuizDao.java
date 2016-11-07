package com.kamazz.quiz.dao.interfaces;


import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.entity.Quiz;

import java.sql.Connection;
import java.util.List;

public interface QuizDao {
    List<Quiz> getQuizListByThemeId (int themeId,Connection conn ) throws NoSuchEntityException,DaoSystemException;

}
