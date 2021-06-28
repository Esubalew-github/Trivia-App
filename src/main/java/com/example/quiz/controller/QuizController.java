package com.example.quiz.controller;

import com.example.quiz.apicall.TriviaQuestion;
import com.example.quiz.apicall.WebServiceRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.reactivex.schedulers.Schedulers;

import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping
public class QuizController {

    private final WebServiceRepository webServiceRepository = new WebServiceRepository();
    private static List<TriviaQuestion> questionRepo = new ArrayList<TriviaQuestion>();

    @GetMapping(path = "/questions", produces = APPLICATION_JSON_VALUE)

    public ResponseEntity<Object> getQuestion(@RequestParam Map<String, String> json) throws InterruptedException {

        questionRepo = webServiceRepository.getTriviaResult(json.get("category"), json.get("difficulty"))
                .subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).blockingGet().getQuestions();

        return new ResponseEntity<>(questionRepo, HttpStatus.OK);

    }

    @PostMapping(path = "/checkanswer/{currQues}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> produceResponse(@PathVariable int currQues, @RequestBody Map<String, String> json) {

        List<Object> responseList = new ArrayList<Object>();
        responseList.add(Objects.equals(questionRepo.get(currQues).getCorrect_answer(), json.get("userAnswer")));
        responseList.add(questionRepo.get(currQues).getCorrect_answer());

        return new ResponseEntity<>(responseList, HttpStatus.OK);

    }

}
