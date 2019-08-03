package com.mehmet.kursdunyasi.Models;

public class ExamsListModel {

    String examName;
    int imageResource;

    public ExamsListModel(String examName, int imageResource) {
        this.examName = examName;
        this.imageResource = imageResource;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
