package com.example.quiz.controller;

import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuizController.class)
public class QuizControllerTest {

        @Autowired
        private MockMvc mvc;

        /*
         * Unit Testing the GET Request Handler
         */

        @Test
        public void testGetQuestions() throws Exception {

                LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
                requestParams.add("category", "10");
                requestParams.add("difficulty", "easy");

                mvc.perform(get("/questions").params(requestParams)).andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$", hasSize(5)));
        }

        /*
         * Unit Testing the POST Request Handler
         */

        @Test
        public void testCheckAnswer_whenCorrectAnswerProvided() throws Exception {

                LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
                requestParams.add("category", "10");
                requestParams.add("difficulty", "easy");

                String questionRepo = mvc.perform(get("/questions").params(requestParams)).andExpect(status().isOk())
                                .andReturn().getResponse().getContentAsString();

                JSONArray questionRepoJson = new JSONArray(questionRepo);

                int currQues = 1;
                JSONObject userAnswerJson = new JSONObject();
                userAnswerJson.put("userAnswer", questionRepoJson.getJSONObject(1).getString("correct_answer"));
                String requestBody = userAnswerJson.toString();

                mvc.perform(post(String.format("/checkanswer/%d", currQues)).contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                                .andExpect(jsonPath("$.[0]").value(true)).andExpect(jsonPath("$.[1]")
                                                .value(questionRepoJson.getJSONObject(1).getString("correct_answer")));

        }

        @Test
        public void testCheckAnswer_whenIncorrectAnswerProvided() throws Exception {

                LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
                requestParams.add("category", "10");
                requestParams.add("difficulty", "easy");

                String questionRepo = mvc.perform(get("/questions").params(requestParams)).andExpect(status().isOk())
                                .andReturn().getResponse().getContentAsString();

                JSONArray questionRepoJson = new JSONArray(questionRepo);

                int currQues = 1;
                JSONObject userAnswerJson = new JSONObject();
                userAnswerJson.put("userAnswer",
                                questionRepoJson.getJSONObject(1).getString("incorrect_answers").split(",")[1]);
                String requestBody = userAnswerJson.toString();

                mvc.perform(post(String.format("/checkanswer/%d", currQues)).contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                                .andExpect(jsonPath("$.[0]").value(false)).andExpect(jsonPath("$.[1]")
                                                .value(questionRepoJson.getJSONObject(1).getString("correct_answer")));
        }

        @Test
        public void testCheckAnswer_whenNoAnswerProvided() throws Exception {

                int currQues = 1;

                mvc.perform(post(String.format("/checkanswer/%d", currQues))).andExpect(status().isBadRequest());
        }

}
