package com.example.demo.models.Quiz;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_scores")
public class UserScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Integer score;
    private Long quizId; // Add this field to associate the score with a specific quiz

    public UserScore() {
    }


    public UserScore(Long userId, Integer score, Long quizId) { // Update constructor to include quizId
        this.userId = userId;
        this.score = score;
        this.quizId = quizId;
    }
    public Long getQuizId() {
        return this.quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}