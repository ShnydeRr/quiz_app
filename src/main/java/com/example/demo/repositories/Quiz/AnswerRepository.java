package com.example.demo.repositories.Quiz;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Quiz.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
