package com.example.demo.controllers.Quiz;
import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.Quiz.Question;
import com.example.demo.services.Quiz.AnswerService;
import com.example.demo.services.Quiz.QuestionService;
import com.example.demo.services.Quiz.UserScoreService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserQuizController {
    
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserScoreService userScoreService;

    public UserQuizController(QuestionService questionService, AnswerService answerService, UserScoreService userScoreService) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.userScoreService = userScoreService;
    }

    // Show the first question of the quiz
    @GetMapping("/start_quiz/{quizId}")
    public String showQuiz(@PathVariable Long quizId, 
                           @RequestParam(value = "questionIndex", defaultValue = "0") int questionIndex, 
                           Model model) {
        List<Question> questions = questionService.getQuestionsByQuizId(quizId);
    
        // Log the number of questions retrieved
        System.out.println("Retrieved questions count for quiz ID " + quizId + ": " + questions.size());
    
        if (questionIndex < questions.size()) {
            // Log the first question details
            Question currentQuestion = questions.get(questionIndex);
            System.out.println("    : " + currentQuestion.getQuestionText()); // Adjust based on your Question model
            
            model.addAttribute("question", currentQuestion);
            model.addAttribute("questionIndex", questionIndex);
            model.addAttribute("quizId", quizId);
            return "Quiz/home/quiz";
        } else {
            return "redirect:/user/quiz/result?quizId=" + quizId;
        }
        }
        @PostMapping("/submit-quiz")
        public String submitQuiz(@RequestParam(value = "answerIds", required = false) List<Long> answerIds,
        @RequestParam("questionId") Long questionId,
        @RequestParam("quizId") Long quizId,
        @RequestParam("questionIndex") int questionIndex,  // Add questionIndex param
        HttpSession session) {
        
            // Retrieve current score or initialize it
            Integer currentScore = (Integer) session.getAttribute("currentScore");
            if (currentScore == null) {
                currentScore = 0; // Initialize if not present
            }
        
            // Fetch the correct answer IDs for the current question
            List<Long> correctAnswerIds = answerService.getCorrectAnswerIdsForQuestion(questionId);
        
            System.out.println("Correct Answer IDs: " + correctAnswerIds);  // Log the correct answers
            System.out.println("Selected Answer IDs: " + answerIds);  // Log the user's selected answers
        
            // Logic to check if all selected answers are correct
            boolean isAllCorrect = true;
        
            // Check if answers are selected
            if (answerIds != null && !answerIds.isEmpty()) {
                // First, check if the number of selected answers matches the number of correct answers
                if (correctAnswerIds.size() != answerIds.size()) {
                    isAllCorrect = false;
                } else {
                    // Check if any of the selected answers are incorrect
                    for (Long answerId : answerIds) {
                        if (!correctAnswerIds.contains(answerId)) {
                            isAllCorrect = false;
                            break;  // Exit the loop once an incorrect answer is found
                        }
                    }
                }
            } else {
                isAllCorrect = false; // No answers selected
            }
        
            // Update the score if all answers were correct
            if (isAllCorrect) {
                currentScore++;
                System.out.println("Question " + questionId + ": Correct! Total Score: " + currentScore);
            } else {
                System.out.println("Question " + questionId + ": Incorrect. Total Score: " + currentScore);
            }
        
            // Update score in session
            session.setAttribute("currentScore", currentScore);

            // Fetch all questions in the quiz to check if there is a next question
            List<Question> questions = questionService.getQuestionsByQuizId(quizId);
        
            // Check if there are more questions
            if (questionIndex + 1 < questions.size()) {
                // Redirect to the next question in the quiz by incrementing questionIndex
                return "redirect:/user/start_quiz/" + quizId + "?questionIndex=" + (questionIndex + 1);
            } else {
                // Quiz is complete, redirect to the quiz completion page
                return "redirect:/user/quiz/result?quizId=" + quizId;
            }
        }
        



        @GetMapping("/quiz/result")
        public String showResult(HttpSession session, Model model, Principal principal, @RequestParam("quizId") Long quizId) {
            Integer finalScore = (Integer) session.getAttribute("currentScore");
            if (finalScore == null) {
                finalScore = 0; // Set to 0 if no score was found
            }
        
            // Retrieve the total number of questions for the quiz
            List<Question> questions = questionService.getAllQuestionsByQuizId(quizId); // Adjust this if you have a method to get specific quiz questions
            int totalQuestions = questions.size();
            System.out.println("eaeaea"+totalQuestions);
            // Save the final score for the user
            String username = principal.getName();
            Long userId = userScoreService.getUserIdByUsername(username);
            userScoreService.saveUserScore(userId, finalScore, quizId); // Save with quizId
        
            // Reset the score after the quiz is finished
            session.setAttribute("currentScore", 0);
        
            // Add attributes to the model
            model.addAttribute("score", finalScore);
            model.addAttribute("totalQuestions", totalQuestions); // Pass total number of questions
            model.addAttribute("quizId", quizId); // Pass quizId for linking
        
            return "Quiz/home/quiz_result"; // Return the view for quiz results
        }
        

}
