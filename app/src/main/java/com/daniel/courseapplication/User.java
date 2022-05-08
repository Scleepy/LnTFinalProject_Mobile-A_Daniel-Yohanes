package com.daniel.courseapplication;

public class User {

    private String name;
    private String email;
    private String password;
    private String courseID;
    private Integer progressToday;
    private Integer progressTotal;
    private Integer progressCounter;
    private Integer totalExp;

    public User(){}

    public User(String name, String email, String password, String courseID, Integer progressToday, Integer progressTotal, Integer progressCounter, Integer totalExp) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.courseID = courseID;
        this.progressToday = progressToday;
        this.progressTotal = progressTotal;
        this.progressCounter = progressCounter;
        this.totalExp = totalExp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public Integer getProgressToday() {
        return progressToday;
    }

    public void setProgressToday(Integer progressToday) {
        this.progressToday = progressToday;
    }

    public Integer getProgressTotal() {
        return progressTotal;
    }

    public void setProgressTotal(Integer progressTotal) {
        this.progressTotal = progressTotal;
    }

    public Integer getProgressCounter() {
        return progressCounter;
    }

    public void setProgressCounter(Integer progressCounter) {
        this.progressCounter = progressCounter;
    }

    public Integer getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(Integer totalExp) {
        this.totalExp = totalExp;
    }
}
