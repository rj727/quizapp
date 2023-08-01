package com.rj.quizapp.controller;

import com.rj.quizapp.dao.QuizDao;
import com.rj.quizapp.model.QuestionWrapper;
import com.rj.quizapp.model.Response;
import com.rj.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,@RequestParam Integer numQ,@RequestParam String title)
    {

        return quizService.createQuizByCategory(category,numQ,title);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable("id") Integer id)
    {
        return quizService.getQuizQuestions(id);

    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> calculateScore(@PathVariable("id")Integer id,@RequestBody List<Response> responses)
    {
        return quizService.calculateScoreForQuiz(id,responses);
    }
}
