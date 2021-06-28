package com.example.quiz.apicall;

import io.reactivex.Single;

public class WebServiceRepository {

    private final int QUESTION_AMOUNT = 5;
    private final String QUESTION_TYPE = "multiple";
    private final ApiClient apiClient;

    public WebServiceRepository() {
        apiClient = ApiClient.getInstance();
    }

    public Single<TriviaResult> getTriviaResult(String category, String difficulty) {

        TriviaService service = apiClient.getTriviaClient().create(TriviaService.class);

        /*
         * Mapping of category names to numbers [which are used in the trivia API call]
         * is already done in the frontend. However, it is in a string format & we need
         * to convert it to an integer using parseInt.
         */

        return service.getQuizResults(QUESTION_AMOUNT, Integer.parseInt(category), difficulty, QUESTION_TYPE);
    }

}
