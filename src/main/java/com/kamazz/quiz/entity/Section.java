package com.kamazz.quiz.entity;


public class Section {
    private int id;
    private String caption;

    public Section() {
    }
    public Section(int id, String caption) {
        this.id = id;
        this.caption = caption;
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

    @Override
    public String toString() {
        return "Section{" +
                "caption='" + caption + '\'' +
                ", id=" + id +
                '}';
    }
}
