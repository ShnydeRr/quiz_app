package com.example.demo.repositories.Quiz;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Quiz.UserScore;

public interface UserScoreRepository extends JpaRepository<UserScore, Long> {
    UserScore findByUserIdAndQuizId(Long userId, Long quizId);

}

