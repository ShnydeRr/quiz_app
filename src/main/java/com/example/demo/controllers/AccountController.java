package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.models.Dto.user.UserDto;
import com.example.demo.services.user.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/register")
public class AccountController {
    @Autowired
    private UserService userService;
    @GetMapping
    public String showRegistrationForm(Model model) {
        // Add an empty UserDto to the model
        model.addAttribute("userDto", new UserDto());
        return "register"; // Refers to register.html in src/main/resources/templates
    }
    @PostMapping
    public String registerUser(@Valid UserDto userDto, BindingResult result, Model model) {
        // Check for validation errors
        if (result.hasErrors()) {
            return "register"; // Return to the registration form with errors
        }

        // Check if email already exists
        if (userService.emailExists(userDto.getEmail())) {
            result.rejectValue("email", "error.userDto", "An account with this email already exists.");
            return "register"; // Return to the registration form with error
        }

        // Check if username already exists
        if (userService.usernameExists(userDto.getUsername())) {
            result.rejectValue("username", "error.userDto", "An account with this username already exists.");
            return "register"; // Return to the registration form with error
        }
        // Register the new user
        userService.registerUser(userDto);

        // Add success message
        model.addAttribute("success", "Registration successful! Please log in.");

        // Redirect to the login page or home page after successful registration
        return "redirect:/login"; // Adjust the redirect URL as needed
    }
    
}