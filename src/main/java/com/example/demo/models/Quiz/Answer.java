package com.example.demo.models.Quiz;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answerText;
    private boolean correct; // Indicates if this is the correct answer
    private Boolean isCorrect;

    public Answer(Long id, String answerText, boolean correct, Boolean isCorrect, Question question) {
        this.id = id;
        this.answerText = answerText;
        this.correct = correct;
        this.isCorrect = isCorrect;
        this.question = question;
    }

    public boolean getCorrect() {
        return this.correct;
    }


    public Boolean isIsCorrect() {
        return this.isCorrect;
    }

    public Boolean getIsCorrect() {
        return this.isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Answer() {
    }

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}