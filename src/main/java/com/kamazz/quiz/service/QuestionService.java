package com.kamazz.quiz.service;

import com.kamazz.quiz.model.Answer;
import com.kamazz.quiz.model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamazz on 15.11.16.
 */
public class QuestionService {
    private int index;
    private int questionListSize;
    List<Question> questionList;

    public QuestionService() {
    }

    public QuestionService(List<Question> questionList) {
        this.questionList = new ArrayList<>(questionList);
        this.questionListSize = questionList.size();
        this.index = 0;
    }
    public void setCurrentQuestionList(List<Question> questionList,int oldIndex) {
        this.questionList = new ArrayList<>(questionList);
        this.questionListSize = questionList.size();
        this.index = oldIndex;
    }

    public QuestionService(QuestionService questionService) {
        this.index = questionService.getIndex();
        this.questionListSize = questionService.getQuestionListSize();
        this.questionList = new ArrayList<>(questionService.getQuestionList());
    }
    public void addUserAnswerToCurrentQuestion(int userAnswerId){
        int answerListSize = getQuestionList().get(getIndex()).getAnswerList().size();
        List<Answer> currentAnswerList = new ArrayList<>(getQuestionList().get(getIndex()).getAnswerList());
        for (int i = 0; i < answerListSize; i++) {
            if (currentAnswerList.get(i).getIdAnswer() == userAnswerId) {
                Answer userAnswer = new Answer(getQuestionList().get(getIndex()).getAnswerList().get(i));
                getQuestionList().get(getIndex()).setUserAnswer(userAnswer);
            }
        }
    }
    public boolean isAnswerListContainsUserAnswer(int userAnswerId){
        int answerListSize = getQuestionList().get(getIndex()).getAnswerList().size();
        List<Answer> currentAnswerList = new ArrayList<>(getQuestionList().get(getIndex()).getAnswerList());
        for (int i = 0; i < answerListSize; i++) {
            if (currentAnswerList.get(i).getIdAnswer() == userAnswerId) {
                return true;
            }
        }
        return false;
    }
    public String getcurrentUserAnswer(){
        return getQuestionList().get(index).getUserAnswer().getAnswer();

    }
    public boolean isLastQuestion() {
        this.setIndex(++index);
        if (this.getIndex() < this.getQuestionList().size()) {
            return false;
        }
        return true;
    }
    public int checkCorrectUserAnswer(){
        int count = 0;
        for (int i = 0; i < getQuestionList().size(); i++) {
            if (getQuestionList().get(i).getUserAnswer().getCorrect() == (byte) 1) {
                count++;
            }
        }
        return count;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getQuestionListSize() {
        return questionListSize;
    }

    public void setQuestionListSize(int questionListSize) {
        this.questionListSize = questionListSize;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}
