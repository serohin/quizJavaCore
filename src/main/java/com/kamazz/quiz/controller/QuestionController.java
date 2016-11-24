package com.kamazz.quiz.controller;


import com.kamazz.injection.DependencyInjectionServlet;
import com.kamazz.injection.Inject;
import com.kamazz.quiz.dao.datasource.JNDIDatasource;
import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.dao.interfaces.QuestionDao;
import com.kamazz.quiz.model.Question;
import com.kamazz.quiz.service.QuestionService;
import com.kamazz.quiz.validator.RequestParameterValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableList;

public class QuestionController extends DependencyInjectionServlet {
    public static final String PARAM_QUIZ_ID = "quizId";
    public static final String PARAM_USER_ANSWER_ID = "userAnswerId";
    public static final String PARAM_ERROR_INPUT = "errorInput";

    public static final String PAGE_QUESTION = "WEB-INF/view/question.jsp";
    public static final String PAGE_ERROR = "WEB-INF/view/question.jsp";
    public static final String PAGE_QUIZ_RESULT = "WEB-INF/view/quizResult.jsp";

    public static final String ATTRIBUTE_MODEL_TO_VIEW = "question";
    public static final String ATTRIBUTE_CURRENT_QUESTION_INDEX = "quizIndex";
    public static final String ATTRIBUTE_COUNT_CORRECT_ANSWER = "countCorrectAnswer";


    @Inject("questionDaoImpl")
    QuestionDao questionDao;

    @Inject("requestParameterValidatorImpl")
    RequestParameterValidator paramValidator;

    @Inject("jndiDatasource")
    JNDIDatasource jndiDatasource;

    @Inject("questionService")
    QuestionService questionService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(SectionController.PAGE_ERROR).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> errorMapQuizId = paramValidator.validateId(req.getParameter(PARAM_QUIZ_ID));

        if (errorMapQuizId.isEmpty()) {
            int id = Integer.valueOf(req.getParameter(PARAM_QUIZ_ID));
            try (Connection conn = jndiDatasource.getDataSource().getConnection()) {
                conn.setAutoCommit(false);
                try {
                    List<Question> questionList = questionDao.getQuestionListByQuizId(id, conn);
                    final int index = 0;
                    HttpSession session = req.getSession(true);
                    session.setAttribute(ATTRIBUTE_MODEL_TO_VIEW, unmodifiableList(questionList));
                    session.setAttribute(ATTRIBUTE_CURRENT_QUESTION_INDEX, index);
                    req.getRequestDispatcher(PAGE_QUESTION).forward(req, resp);
                } catch (DaoSystemException e) {
                    conn.rollback();
                    //logger.debug(e);
                } catch (NoSuchEntityException e) {
                    //logger.debug(e);
                }
                conn.commit();
            } catch (SQLException e) {
                //logger.debug(e);
            }
        } else {
            Map<String, String> errorMapUserAnswerId = paramValidator.validate(req.getParameter(PARAM_USER_ANSWER_ID));

            if (errorMapUserAnswerId.isEmpty()) {
                HttpSession session = req.getSession(true);
                List<Question> oldlistQuestion = new ArrayList((List<Question>) session.getAttribute(ATTRIBUTE_MODEL_TO_VIEW));
                int oldIndex = (int) session.getAttribute(ATTRIBUTE_CURRENT_QUESTION_INDEX);
                questionService.setCurrentQuestionList(oldlistQuestion, oldIndex);
                int userAnswerId = Integer.valueOf(req.getParameter(PARAM_USER_ANSWER_ID));

                if (!questionService.idContainsInAnswerList(userAnswerId)) {
                    req.setAttribute(PARAM_ERROR_INPUT, "выберите 1 из значений!");
                    req.getRequestDispatcher(PAGE_ERROR).forward(req, resp);
                    return;
                }
                questionService.addUserAnswerToCurrentQuestion(userAnswerId);
                session.setAttribute(ATTRIBUTE_MODEL_TO_VIEW, unmodifiableList(questionService.getQuestionList()));

                if (!questionService.lastQuestionInQuiz()) {
                    final int nextIndex = questionService.getIndex();
                    session.setAttribute(ATTRIBUTE_CURRENT_QUESTION_INDEX, nextIndex);
                    req.getRequestDispatcher(PAGE_QUESTION).forward(req, resp);
                } else {
                    final int numberCorrectAnswers = questionService.calculateCorrectUserAnswers();
                    session.setAttribute(ATTRIBUTE_COUNT_CORRECT_ANSWER, numberCorrectAnswers);
                    req.getRequestDispatcher(PAGE_QUIZ_RESULT).forward(req, resp);
                }
                return;
            }
            req.setAttribute(PARAM_ERROR_INPUT, "выберите 1 из значений!");
            req.getRequestDispatcher(PAGE_ERROR).forward(req, resp);
        }
    }
}
