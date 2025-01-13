package com.example.demo.services.Quiz;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.models.Quiz.Question;
import com.example.demo.repositories.Quiz.QuestionRepository;
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    // Method to get all questions
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // Method to get a question by ID
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID: " + id));
    }
    
    // Method to get a question by index
    public Question getQuestionByIndex(int index) {
        List<Question> questions = questionRepository.findAll();
        if (index >= 0 && index < questions.size()) {
            return questions.get(index);
        }
        return null;  // Return null if index is out of bounds
    }

    public List<Question> getQuestionsByQuizId(Long quizId) {
        return questionRepository.findByQuizId(quizId); // Assuming you have a method in your repository
    }

    // Method to get the first question
    public Question getFirstQuestion() {
        return getAllQuestions().stream().findFirst().orElse(null);  // Get the first question
    }

    // Method to save a new question
    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }
    public List<Question> getAllQuestionsByQuizId(Long quizId) {
        return questionRepository.findByQuizId(quizId); // Fetch questions for the given quiz ID
    }
    public Question getFirstQuestionByQuizId(Long quizId) {
        return questionRepository.findFirstByQuizId(quizId);
    }
    public boolean checkAnswer(Long questionId, Long userAnswerId) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question != null) {
            // Assuming Answer has a field 'id' and there's a method to get correct answer ID
            return question.getAnswers().stream()
                .filter(answer -> answer.getId().equals(userAnswerId))
                .anyMatch(answer -> answer.isCorrect()); // Assuming you have an 'isCorrect()' method in Answer
        }
        return false; // Question not found, treat as incorrect
    }
   // In your QuestionService
public Optional<Question> findNextQuestion(Long quizId, Long currentQuestionId) {
    // Fetch all questions for the quiz ordered by their IDs
    List<Question> questions = questionRepository.findByQuizIdOrderByIdAsc(quizId);
    
    // Find the index of the current question
    for (int i = 0; i < questions.size(); i++) {
        if (questions.get(i).getId().equals(currentQuestionId)) {
            // Check if there is a next question
            if (i + 1 < questions.size()) {
                return Optional.of(questions.get(i + 1));  // Return the next question
            }
            break;  // If we're at the last question, break the loop
        }
    }
    
    // If no next question exists, return an empty optional
    return Optional.empty();
}

    
    
    
}
