package com.kamazz.quiz.model;


public class Answer {
    private int idAnswer;
    private String answer;
    private byte correct;
    private int questionId;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
    /*private Question question;*/

    public Answer() {
    }

    public Answer(int idAnswer, String answer, byte correct, int questionId) {
        this.idAnswer = idAnswer;
        this.answer = answer;
        this.correct = correct;
        this.questionId = questionId;
    }

    public Answer(Answer answer) {
        this.idAnswer = answer.getIdAnswer();
        this.answer = answer.getAnswer();
        this.correct = answer.getCorrect();
        this.questionId = answer.getQuestionId();
    }


    public int getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(int idAnswer) {
        this.idAnswer = idAnswer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public byte getCorrect() {
        return correct;
    }

    public void setCorrect(byte correct) {
        this.correct = correct;
    }

    /*public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }*/

    @Override
    public String toString() {
        return "Answer{" +
                "idAnswer=" + idAnswer +
                ", answer='" + answer + '\'' +
                ", correct=" + correct +
                ", questionId=" + questionId +
                '}';
    }
}
