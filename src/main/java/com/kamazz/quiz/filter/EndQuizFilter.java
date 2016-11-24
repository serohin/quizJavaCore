package com.kamazz.quiz.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class EndQuizFilter extends BaseFilter {
    public static final String PAGE_OK = "WEB-INF/view/quiz.jsp";
    public static final String ATTRIBUTE_MODEL_TO_VIEW = "question";
    public static final String ATTRIBUTE_CURRENT_QUESTION_INDEX = "quizIndex";
    public static final String ATTRIBUTE_COUNT_CORRECT_ANSWER = "countCorrectAnswer";

    @Override
    public void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException, ServletException {

        //remove some session attributes
        HttpSession session = req.getSession(true);
        session.removeAttribute(ATTRIBUTE_MODEL_TO_VIEW);
        session.removeAttribute(ATTRIBUTE_CURRENT_QUESTION_INDEX);
        session.removeAttribute(ATTRIBUTE_COUNT_CORRECT_ANSWER);

        req.getRequestDispatcher(PAGE_OK).forward(req, resp);


    }
}
