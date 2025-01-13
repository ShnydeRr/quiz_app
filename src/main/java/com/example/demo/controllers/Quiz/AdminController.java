package com.example.demo.controllers.Quiz;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.models.Quiz.Quiz;
import com.example.demo.services.Quiz.QuizService;

@Controller
@RequestMapping("/admin/quizzes")
public class AdminController {

    private final QuizService quizService;

    public AdminController(QuizService quizService) {
        this.quizService = quizService;
    }

    // Display all quizzes for admin approval
    @GetMapping
    public String listQuizzes(Model model) {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        model.addAttribute("quizzes", quizzes);
        return "admin_profile/quizzes/list"; // Thymeleaf template for listing quizzes
    }

    // Approve a quiz
    @PostMapping("/approve/{id}")
    public String approveQuiz(@PathVariable Long id) {
        Quiz quiz = quizService.getQuizById(id);
        if (quiz != null) {
            quiz.setApproved(true); // Set quiz as approved
            quizService.saveQuiz(quiz); // Save the updated quiz
        }
        return "redirect:/admin/quizzes"; // Redirect back to the quizzes list
    }

    // Delete a quiz
    @DeleteMapping("/{id}")
    public String deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id); // Delete the quiz by ID
        return "redirect:/admin/quizzes"; // Redirect back to the quizzes list
    }
        // Other methods for approving quizzes, etc.
    @GetMapping("/{id}")
    public String viewQuiz(@PathVariable Long id, Model model) {
        Quiz quiz = quizService.getQuizById(id); // Get the quiz by ID
        model.addAttribute("quiz", quiz); // Add the quiz to the model
        return "admin_profile/quizzes/view"; // Thymeleaf template for viewing a quiz
    }

    // Approve a Quiz
    @PostMapping("/approve")
    public String approveQuiz(@RequestParam Long quizId, RedirectAttributes redirectAttributes) {
        Quiz quiz = quizService.getQuizById(quizId);
        if (quiz != null) {
            quiz.setApproved(true); // Set approved status to true
            quizService.saveQuiz(quiz); // Save changes to the quiz
            redirectAttributes.addFlashAttribute("message", "Quiz approved successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Quiz not found.");
        }
        return "redirect:/admin/quizzes"; // Redirect to quizzes list
    }

    // Reject a Quiz
    @PostMapping("/reject")
    public String rejectQuiz(@RequestParam Long quizId, RedirectAttributes redirectAttributes) {
        Quiz quiz = quizService.getQuizById(quizId);
        if (quiz != null) {
            quiz.setApproved(false); // Set approved status to false
            quizService.saveQuiz(quiz); // Save changes to the quiz
            redirectAttributes.addFlashAttribute("message", "Quiz rejected successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Quiz not found.");
        }
        return "redirect:/admin/quizzes"; // Redirect to quizzes list
    }
    @PostMapping("/delete")
    public String deleteQuiz(@RequestParam Long quizId, RedirectAttributes redirectAttributes) {
        Quiz quiz = quizService.getQuizById(quizId);
        if (quiz != null) {
            quizService.deleteQuizById(quizId); // Delete the quiz
            redirectAttributes.addFlashAttribute("message", "Quiz deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Quiz not found.");
        }
        return "redirect:/admin/quizzes"; // Redirect to quizzes list
    }
}