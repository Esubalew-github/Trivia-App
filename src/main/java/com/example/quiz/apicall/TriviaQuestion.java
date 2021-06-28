package com.example.quiz.apicall;

import com.google.gson.annotations.SerializedName;

public class TriviaQuestion {

    private String question;
    @SerializedName("correct_answer")
    private String correct_answer;
    private String category;
    private String difficulty;
    @SerializedName("incorrect_answers")
    private String[] incorrect_answers;

    public String getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getIncorrect_answers() {
        return incorrect_answers;
    }

}
