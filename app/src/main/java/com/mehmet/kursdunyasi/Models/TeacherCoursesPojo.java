package com.mehmet.kursdunyasi.Models;

public class TeacherCoursesPojo {

    private String cover_name;
    private String title;
    private String path;
    private String folder;
    int education_id;

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public TeacherCoursesPojo(String cover, String description, String path, int education_id, String folder) {
        this.cover_name = cover;
        this.title = description;
        this.path = path;
        this.education_id = education_id;
        this.folder = folder;
    }

    public int getEducation_id() {
        return education_id;
    }

    public void setEducation_id(int education_id) {
        this.education_id = education_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCover_name() {
        return cover_name;
    }

    public void setCover_name(String cover_name) {
        this.cover_name = cover_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
