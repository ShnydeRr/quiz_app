package com.example.demo.controllers.Quiz;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.models.User;
import com.example.demo.models.Dto.Quiz.QuizDto;
import com.example.demo.models.Quiz.Quiz;
import com.example.demo.services.AuthenticationService;
import com.example.demo.services.Quiz.QuizService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/quizzes")
public class QuizController {

    private final QuizService quizService;
    private final AuthenticationService authenticationService;

    

    public QuizController(QuizService quizService , AuthenticationService authenticationService) {
        this.quizService = quizService;
        this.authenticationService = authenticationService;
    }

    // Show the form to create a new quiz
    @GetMapping("/create")
    public String showCreateQuizForm(Model model) {
        model.addAttribute("quizDto", new QuizDto());
        return "quiz/quizzes/create"; // Thymeleaf template for creating a quiz
    }

    // Process the quiz submission
    @PostMapping("/create")
    public String createQuiz(@Valid @ModelAttribute QuizDto quizDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "quiz/quizzes/create"; // Return to the form if there are validation errors
        }
        quizService.saveQuiz(quizDto);
        return "redirect:/user/quizzes/my_quizzes"; // Redirect to the list of quizzes after successful creation
    }


    // Other methods for approving quizzes, etc.
    @GetMapping("/{id}")
    public String viewQuiz(@PathVariable Long id, Model model) {
        Quiz quiz = quizService.getQuizById(id); // Get the quiz by ID
        model.addAttribute("quiz", quiz); // Add the quiz to the model
        return "quiz/quizzes/view"; // Thymeleaf template for viewing a quiz
    }
        
    @GetMapping("/my_quizzes")
    public String myQuizzes(Model model) {
        User currentUser = null;
        try {
            // Fetch the currently logged-in user
            currentUser = authenticationService.getCurrentUser();
        } catch (Exception e) {
            System.out.println("Error fetching current user: " + e.getMessage());
            // Optionally, you can redirect to an error page or handle the error as needed
            return "error"; // Change to your error page
        }

        // Fetch quizzes for the current user
        List<Quiz> userQuizzes = quizService.getQuizzesByUser(currentUser);
        model.addAttribute("quizzes", userQuizzes);
        return "quiz/quizzes/my_quizzes"; // Thymeleaf template for my quizzes
    }

    
    }
