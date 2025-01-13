package com.example.demo.services.Quiz;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Dto.Quiz.AnswerDto;
import com.example.demo.models.Quiz.Answer;
import com.example.demo.models.Quiz.Question;
import com.example.demo.repositories.Quiz.AnswerRepository;
import com.example.demo.repositories.Quiz.QuestionRepository;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository; // Add this

    // Method to save a list of answers
    public void saveAnswers(List<AnswerDto> answerDtos, Long questionId) {
        // Fetch the Question entity
        Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new RuntimeException("Question not found")); // Handle if question not found

        for (AnswerDto answerDto : answerDtos) {
            Answer answer = new Answer();
            answer.setAnswerText(answerDto.getAnswerText()); // Use the correct setter for the text
            answer.setCorrect(answerDto.isCorrect());

            // Set the Question object instead of just the ID
            answer.setQuestion(question); // Set the relationship
            answerRepository.save(answer);
        }
    }
    
    // Method to check if a given answer is correct
    public boolean isCorrectAnswer(Long answerId) {
        System.out.println("Checking if answer with ID " + answerId + " is correct.");
        
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid answer ID: " + answerId));
        
        boolean isCorrect = answer.isCorrect(); // Now correctly accessing the `correct` field
        System.out.println("Answer is " + (isCorrect ? "correct" : "incorrect"));
        
        return isCorrect;
    }


    // Fetches the correct answer IDs for the given question
    public List<Long> getCorrectAnswerIdsForQuestion(Long questionId) {
        // Fetch the question by its ID
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID: " + questionId));

        // Extract correct answers
        return question.getAnswers().stream()
                .filter(Answer::isCorrect) // Check if the answer is correct
                .map(Answer::getId) // Get the ID of the correct answers
                .collect(Collectors.toList());
    }
    

}
