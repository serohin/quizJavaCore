package com.kamazz.quiz.model;


import java.util.List;

public class Question {
    private int questionId;
    private String caption;
    private String question;
    private int quizId;
    private List<Answer> answerList;
    private Answer userAnswer;

    public Question() {
    }

    public Question(int questionId, String caption, String question, int quizId, List<Answer> answerList, Answer userAnswer) {

        this.questionId = questionId;
        this.caption = caption;
        this.question = question;
        this.quizId = quizId;
        this.answerList = answerList;
        this.userAnswer = userAnswer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public Answer getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(Answer userAnswer) {
        this.userAnswer = userAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", caption='" + caption + '\'' +
                ", question='" + question + '\'' +
                ", quizId=" + quizId +
                ", answerList=" + answerList +
                ", userAnswer=" + userAnswer +
                '}';
    }
}
