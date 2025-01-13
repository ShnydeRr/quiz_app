package com.example.demo.models.Dto.Quiz;

import jakarta.validation.constraints.NotBlank;

public class AnswerDto {
    @NotBlank(message = "Answer text is required.")
    private String answerText;

    private boolean correct; // Indicates if this is the correct answer

    // Getters and Setters
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
}