package com.rj.quizapp.dao;

import com.rj.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findAllByCategory(String category);

    @Query(value = "Select * from question where category =:category order by Random() Limit(:numQ)",nativeQuery = true)
    List<Question> findQuestionsByCategory(@Param("category") String category,@Param("numQ") Integer numQ);
}
