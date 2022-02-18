package com.quiz;

public class TriviaQuestionModel {
    private int id;
    private String name;
    private String totalQuestions;
    private String correctAnswers;
    private String test1;

    public TriviaQuestionModel(String playerName, String totalQ, String totalA, String tst1) {
        name = playerName;
        totalQuestions = totalQ;
        correctAnswers = totalA;
        test1 = tst1;
    }

    public TriviaQuestionModel() {
        id = 0;
        name = "";
        totalQuestions = "";
        correctAnswers = "";
        test1 = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(String totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(String correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public String getTest1() {
        return test1;
    }

    public void setTest1(String test1) {
        this.test1 = test1;
    }
}
