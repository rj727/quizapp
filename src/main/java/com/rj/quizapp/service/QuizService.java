package com.rj.quizapp.service;

import com.rj.quizapp.dao.QuestionRepository;
import com.rj.quizapp.dao.QuizDao;
import com.rj.quizapp.model.Question;
import com.rj.quizapp.model.QuestionWrapper;
import com.rj.quizapp.model.Quiz;
import com.rj.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionRepository questionRepository;
    public ResponseEntity<String> createQuizByCategory(String category, Integer numQ,String title) {

        List<Question> questions=questionRepository.findQuestionsByCategory(category,numQ);

        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionList(questions);

        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz=quizDao.findById(id);
        List<Question> questionsFromDb=quiz.get().getQuestionList();
        List<QuestionWrapper> questionsForUser=new ArrayList<>();
        for(Question q: questionsFromDb)
        {
            QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionsForUser.add(qw);
        }

        return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateScoreForQuiz(Integer id, List<Response> responses) {
        Optional<Quiz> quiz=quizDao.findById(id);
        List<Question>questionList=quiz.get().getQuestionList();

        int index=0;
        int correctAnswers=0;

        //order of questions and responses will be same.
        for(Response response:responses)
        {
            if(response.getResponse().equals(questionList.get(index).getRightAnswer()))
            {
                correctAnswers++;
            }
            index++;

        }
        return new ResponseEntity<>(correctAnswers,HttpStatus.OK);
    }
}
