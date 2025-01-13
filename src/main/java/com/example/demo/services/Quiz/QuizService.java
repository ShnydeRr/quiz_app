package com.example.demo.services.Quiz;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.Dto.Quiz.AnswerDto;
import com.example.demo.models.Dto.Quiz.QuestionDto;
import com.example.demo.models.Dto.Quiz.QuizDto;
import com.example.demo.models.Quiz.Answer;
import com.example.demo.models.Quiz.Question;
import com.example.demo.models.Quiz.Quiz;
import com.example.demo.models.User;
import com.example.demo.repositories.Quiz.AnswerRepository;
import com.example.demo.repositories.Quiz.QuestionRepository;
import com.example.demo.repositories.Quiz.QuizRepository;
import com.example.demo.repositories.UserRepository;

@Service
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository; // Assuming you have a UserRepository

    public QuizService(QuizRepository quizRepository,
                       QuestionRepository questionRepository,
                       AnswerRepository answerRepository,
                       UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
    }public void saveQuiz(QuizDto quizDto) {
        // Get the currently logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        
        // Find the user directly
        User currentUser = userRepository.findByUsername(username); // This may throw an exception if the user is not found
    
        // Save the quiz entity
        Quiz quiz = new Quiz();
        quiz.setTitle(quizDto.getTitle());
        quiz.setApproved(false); // Admin needs to approve it first
        quiz.setUser(currentUser); // Associate the quiz with the current user
        quiz = quizRepository.save(quiz);
    
        // Save the questions and answers
        for (QuestionDto questionDto : quizDto.getQuestions()) {
            Question question = new Question();
            question.setQuiz(quiz);
            question.setQuestionText(questionDto.getQuestionText());
            question = questionRepository.save(question);
    
            for (AnswerDto answerDto : questionDto.getAnswers()) {
                Answer answer = new Answer();
                answer.setQuestion(question);
                answer.setAnswerText(answerDto.getAnswerText());
                answer.setCorrect(answerDto.isCorrect());
                answerRepository.save(answer);
            }
        }
    }
    
    public Quiz findQuizById(Long quizId) {
        return quizRepository.findById(quizId).orElse(null); // Fetch quiz by ID
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid quiz ID: " + id));
    }


    public List<Quiz> getQuizzesByUser(User user) {
        return quizRepository.findByUser(user); // Assuming you have this method in your repository
    }
    public void saveQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }
    
    public void deleteQuizById(Long id) {
        quizRepository.deleteById(id);
    }
    public void approveQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow();
        quiz.setApproved(true); // Assuming you have an 'approved' field
        quizRepository.save(quiz);
    }

    public List<Quiz> getApprovedQuizzes() {
        return quizRepository.findByApproved(true); // Method to find approved quizzes
    }


    public Long getQuestionIdByIndex(Long quizId, int questionIndex) {
        // Fetch the quiz by ID
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid quiz ID: " + quizId));

        // Get the list of questions from the quiz
        List<Question> questions = quiz.getQuestions();

        // Check if the index is valid
        if (questionIndex < 0 || questionIndex >= questions.size()) {
            throw new IllegalArgumentException("Invalid question index: " + questionIndex);
        }

        // Return the ID of the question at the specified index
        return questions.get(questionIndex).getId();
    }
    
    
}