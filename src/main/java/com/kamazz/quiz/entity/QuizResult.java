package com.kamazz.quiz.entity;


public class QuizResult {
    private int id;
    private byte correctAnswer;
    private byte quizLength;
    private int userId;
    private Section section;
    private Theme theme;
    private Quiz quiz;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(byte correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public byte getQuizLength() {
        return quizLength;
    }

    public void setQuizLength(byte quizLength) {
        this.quizLength = quizLength;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public String toString() {
        return "QuizResult{" +
                "id=" + id +
                ", correctAnswer=" + correctAnswer +
                ", quizLength=" + quizLength +
                ", userId=" + userId +
                ", section=" + section +
                ", theme=" + theme +
                ", quiz=" + quiz +
                '}';
    }
}
