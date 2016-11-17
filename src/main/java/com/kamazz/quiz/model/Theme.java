package com.kamazz.quiz.model;


public class Theme {
    private int id;
    private String caption;
    private int sectionId;

    public Theme() {
    }

    public Theme(int id, String caption, int sectionId) {
        this.id = id;
        this.caption = caption;
        this.sectionId = sectionId;
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

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    @Override
    public String toString() {
        return "Theme{" +
                "id=" + id +
                ", caption='" + caption + '\'' +
                ", sectionId=" + sectionId +
                '}';
    }
}
