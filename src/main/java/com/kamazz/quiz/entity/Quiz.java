package com.kamazz.quiz.entity;

public class Quiz {
    private int id;
    private String caption;
    private int themeId;

    public Quiz() {
    }

    public Quiz(int id, String caption, int themeId) {

        this.id = id;
        this.caption = caption;
        this.themeId = themeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", caption='" + caption + '\'' +
                ", themeId=" + themeId +
                '}';
    }
}
