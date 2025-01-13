package com.example.demo.controllers.Quiz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.models.Quiz.Quiz;
import com.example.demo.services.AuthenticationService;
import com.example.demo.services.Quiz.QuizService;



@Controller
@RequestMapping("/user/home")
public class QuizHomeController  {
    @Autowired
    private QuizService quizService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping
    public String home(Model model) {
        List<Quiz> quizzes = quizService.getApprovedQuizzes(); // Fetch approved quizzes
        model.addAttribute("quizzes", quizzes);
        return "quiz/home/home"; // Thymeleaf template for homepage
    }
    }
