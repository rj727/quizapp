package com.rj.quizapp.service;

import com.rj.quizapp.dao.QuestionRepository;
import com.rj.quizapp.model.Question;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class QuestionService {

    static Logger log= Logger.getLogger("QuestionService");
    @Autowired
    QuestionRepository questionRepository;
    public ResponseEntity<List<Question>> getAllQuestions() {
        try
        {
            return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK) ;
        }
        catch (Exception e)
        {
            log.severe(e.getMessage());
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST) ;
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try
        {
            return new ResponseEntity<>(questionRepository.findAllByCategory(category), HttpStatus.OK) ;
        }
        catch (Exception e)
        {
            log.severe(e.getMessage());
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST) ;
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try{
            questionRepository.save(question);
            return new ResponseEntity<>("Success", HttpStatus.CREATED) ;
        }
        catch (Exception e)
        {
            log.severe(e.getMessage());
        }
        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST) ;
    }

    //check if it exists
    public ResponseEntity<String> deleteQuestion(Integer id) {
        try{
            if(questionRepository.existsById(id))
            {
                questionRepository.deleteById(id);
                return new ResponseEntity<>("Deleted question with id: "+id, HttpStatus.OK) ;
            }
            return new ResponseEntity<>("The provided id does not exist", HttpStatus.BAD_REQUEST) ;
        }
        catch (Exception e)
        {
            log.severe(e.getMessage());
        }
        return new ResponseEntity<>("Unknown error", HttpStatus.BAD_REQUEST) ;
    }

    public ResponseEntity<String> updateQuestion(Question question) {
        try{
            questionRepository.save(question);
            return new ResponseEntity<>("Updated question with id: "+question.getId(), HttpStatus.CREATED) ;
        }
        catch (Exception e)
        {
            log.severe(e.getMessage());
        }
        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST) ;
    }

}
