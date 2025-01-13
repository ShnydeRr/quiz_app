package com.example.demo.models.Dto.Quiz;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class QuestionDto {
    
    @NotBlank(message = "Question text is required.")
    private String questionText;

    @NotEmpty(message = "At least one answer is required.")
    private List<AnswerDto> answers;

    // Getters and Setters
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<AnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDto> answers) {
        this.answers = answers;
    }
}