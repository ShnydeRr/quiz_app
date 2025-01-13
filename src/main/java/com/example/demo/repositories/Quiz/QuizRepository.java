package com.example.demo.repositories.Quiz;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Quiz.Quiz;
import com.example.demo.models.User;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByUser(User user);
    List<Quiz> findByApproved(boolean approved);

}
