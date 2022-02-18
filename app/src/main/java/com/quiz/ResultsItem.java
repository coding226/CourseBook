package com.quiz;

import java.io.Serializable;
import java.util.List;

public class ResultsItem implements Serializable {
    private String question;
    private String correctAnswer;
    private List<String> options;
    private int image;

    public ResultsItem() {
    }

    public ResultsItem(String question, String correctAnswer, List<String> options, int image) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.options = options;
        this.image = image;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ResultsItem{" +
                "question='" + question + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", incorrectAnswers=" + options +
                ", image=" + image +
                '}';
    }
}