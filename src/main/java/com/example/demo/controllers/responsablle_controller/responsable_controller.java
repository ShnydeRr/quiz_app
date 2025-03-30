package com.example.demo.controllers.responsablle_controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.Dto.user.UserDto;
import com.example.demo.models.User;
import com.example.demo.services.admin.adminService;
import com.example.demo.services.user.UserService;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/responsable")
public class responsable_controller {
    @Autowired
    private UserService userService;
    @Autowired
    private adminService adminService;
    @GetMapping("/profile")
    public String getProfile(Model model, Authentication authentication) {
        model.addAttribute("message", "Hello, Profile");
    
        // Get the email of the currently logged-in user
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        // Fetch the user details from the service
        User user = userService.findByUsername(username);
        if (user != null) {
            model.addAttribute("user", user);
        }
        // Return the profile view, ensuring the template exists in the correct directory
        return "responsable_M/profile";
    }

    @GetMapping("/manage_users")
    public String listAllUsers(Model model) {
        // Fetch all users
        Iterable<User> allUsers = adminService.findAllUsers();
        
        // Filter users who don't have emails ending with @admin.com or @responsable.com
        List<User> filteredUsers = new ArrayList<>();
        for (User user : allUsers) {
            if (!user.getEmail().endsWith("@admin.com") && !user.getEmail().endsWith("@responsable.com")) {
                filteredUsers.add(user);
            }
        }
    
        // Add filtered users to model
        model.addAttribute("users", filteredUsers);
        return "responsable_M/manage_users"; // Refers to manage_users.html
    }
    


    @PostMapping("/delete_users")
    public String deleteUser(@RequestParam("userId") Long userId) {
        User user = adminService.getUserById(userId);
    
        // Ensure the user exists before deletion
        if (user != null) {
            adminService.deleteUserById(userId);
        }
    
        return "redirect:/responsable/manage_users";
    }
    

    

    @GetMapping("/register_user")
    public String showRegistrationForm(Model model) {
        // Add an empty UserDto to the model
        model.addAttribute("userDto", new UserDto());
        return "responsable_M/add_user"; // Refers to register.html in src/main/resources/templates
    }
    @PostMapping("/register_user")
    public String registerUser(@Valid UserDto userDto, BindingResult result, Model model) {
        // Check for validation errors
        if (result.hasErrors()) {
            return "responsable_M/add_user"; // Return to the registration form with errors
        }

        // Check if email already exists
        if (userService.emailExists(userDto.getEmail())) {
            result.rejectValue("email", "error.userDto", "An account with this email already exists.");
            return "responsable_M/add_user"; // Return to the registration form with error
        }

        // Check if username already exists
        if (userService.usernameExists(userDto.getUsername())) {
            result.rejectValue("username", "error.userDto", "An account with this username already exists.");
            return "responsable_M/add_user"; // Return to the registration form with error
        }
        // Register the new user
        userService.registerUser(userDto);

        // Add success message
        model.addAttribute("success", "Registration successful! Please log in.");

        // Redirect to the login page or home page after successful registration
        return "redirect:/responsable/manage_users";
    }
}
