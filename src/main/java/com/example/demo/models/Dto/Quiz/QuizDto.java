package com.example.demo.models.Dto.Quiz;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;


public class QuizDto {
    @NotBlank(message = "Title is required.")
    private String title;

    @NotEmpty(message = "At least one question is required.")
    private List<QuestionDto> questions;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }
}
