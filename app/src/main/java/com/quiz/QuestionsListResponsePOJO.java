package com.quiz;


import java.io.Serializable;
import java.util.List;

public class QuestionsListResponsePOJO implements Serializable {

    private int responseCode;

    private List<ResultsItem> results;

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResults(List<ResultsItem> results) {
        this.results = results;
    }

    public List<ResultsItem> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return
                "QuestionsListResponsePOJO{" +
                        "response_code = '" + responseCode + '\'' +
                        ",results = '" + results + '\'' +
                        "}";
    }
}