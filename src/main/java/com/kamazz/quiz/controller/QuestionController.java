package com.kamazz.quiz.controller;


import com.kamazz.injection.DependencyInjectionServlet;
import com.kamazz.injection.Inject;
import com.kamazz.quiz.dao.connection.JNDIConnection;
import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.dao.interfaces.QuestionDao;
import com.kamazz.quiz.entity.Answer;
import com.kamazz.quiz.entity.Question;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class QuestionController extends DependencyInjectionServlet {
    public static final String PARAM_QUIZ_ID = "quizId";
    public static final String PARAM_USER_ANSWER_ID = "userAnswerId";

    public static final String PAGE_OK = "WEB-INF/view/question.jsp";
    public static final String PAGE_ERROR = "WEB-INF/view/404error.jsp";
    public static final String PAGE_QUIZ_RESULT = "WEB-INF/view/quizResult.jsp";

    public static final String ATTRIBUTE_CURRENT_QUESTION_LIST = "question";
    public static final String ATTRIBUTE_CURRENT_QUESTION_INDEX = "quizIndex";
    public static final String ATTRIBUTE_COUNT_CORRECT_ANSWER = "countCorrectAnswer";


    @Inject("questionDaoImpl")
    QuestionDao questionDao;
    @Inject("jndiConnection")
    JNDIConnection jndiConnection;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Достаем id выбранного квиза
        String strId = req.getParameter(PARAM_QUIZ_ID);

        if (strId == null) {
            req.getRequestDispatcher(PAGE_ERROR).forward(req, resp);
        } else {

            try {
                Integer.valueOf(strId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                //logger.debug(e);
                req.getRequestDispatcher(PAGE_ERROR).forward(req, resp);
                return;
            }
            int id = Integer.valueOf(strId);

            try (Connection conn = jndiConnection.getConnection()) {
                conn.setAutoCommit(false);
                List<Question> questionList = null;
                try {
                    questionList = questionDao.getQuestionListByQuizId(id, conn);
                } catch (DaoSystemException e) {
                    conn.rollback();
                } catch (NoSuchEntityException e) {
                    e.printStackTrace();
                    //logger.debug(e);
                    req.getRequestDispatcher(PAGE_ERROR).forward(req, resp);
                    ;
                }
                conn.commit();
                if (null != questionList) {
                    final int index = 0;
                    HttpSession session = req.getSession(true);
                    session.setAttribute(ATTRIBUTE_CURRENT_QUESTION_LIST, unmodifiableList(questionList));
                    session.setAttribute(ATTRIBUTE_CURRENT_QUESTION_INDEX, index);
                    req.getRequestDispatcher(PAGE_OK).forward(req, resp);
                }
            } catch (SQLException e) {
                e.printStackTrace();//убрать
                //logger.debug(e);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userAnswerIdStr = req.getParameter(PARAM_USER_ANSWER_ID);
        if (userAnswerIdStr == null) {
            req.setAttribute("errorInput", "выберите 1 из значений!");
            req.getRequestDispatcher(PAGE_OK ).forward(req, resp);
            return;
        }
        int userAnswerId = Integer.valueOf(userAnswerIdStr);
        HttpSession session = req.getSession(true);
        //тащим текущую позицию вопроса
        int oldIndex = (int) session.getAttribute(ATTRIBUTE_CURRENT_QUESTION_INDEX);
        //тащим старый список вопросов
        List<Question> oldlistQuestion = new ArrayList((List<Question>) session.getAttribute(ATTRIBUTE_CURRENT_QUESTION_LIST));

        for (int i = 0; i < oldlistQuestion.get(oldIndex).getAnswerList().size(); i++) {
            if (oldlistQuestion.get(oldIndex).getAnswerList().get(i).getIdAnswer() == userAnswerId) {
                Answer userAnswer = new Answer(oldlistQuestion.get(oldIndex).getAnswerList().get(i));
                oldlistQuestion.get(oldIndex).setUserAnswer(userAnswer);
            }
        }

        if (oldlistQuestion.get(oldIndex).getUserAnswer().getAnswer() == null) {
            req.setAttribute("errorInput", "выберите 1 из значений!");
            req.getRequestDispatcher(PAGE_OK).forward(req, resp);
            return;
        }

        session.setAttribute(ATTRIBUTE_CURRENT_QUESTION_LIST, unmodifiableList(oldlistQuestion));
        final int nextIndex = ++oldIndex;

        if (nextIndex < oldlistQuestion.size()) {
            session.setAttribute(ATTRIBUTE_CURRENT_QUESTION_INDEX, nextIndex);
            req.getRequestDispatcher(PAGE_OK).forward(req, resp);
        } else {
            int count = 0;
            for (int i = 0; i < oldlistQuestion.size(); i++) {
                if (oldlistQuestion.get(i).getUserAnswer().getCorrect() == (byte) 1) {
                    count++;
                }
            }
            final int countCorrectAnswer = count;
            session.setAttribute(ATTRIBUTE_COUNT_CORRECT_ANSWER, countCorrectAnswer);
            req.getRequestDispatcher(PAGE_QUIZ_RESULT).forward(req, resp);
        }


    }
}
