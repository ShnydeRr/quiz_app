package com.example.demo.repositories.Quiz;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.models.Quiz.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
        List<Question> findByQuizId(Long quizId);

        @Query("SELECT q FROM Question q WHERE q.quiz.id = :quizId ORDER BY q.id ASC")
        Question findFirstByQuizId(@Param("quizId") Long quizId);
        public List<Question> findByQuizIdOrderById(Long quizId);
        public List<Question> findByQuizIdOrderByIdAsc(Long quizId);
}
