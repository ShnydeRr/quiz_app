package com.example.demo.controllers.user_controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.models.User;
import com.example.demo.models.Dto.user.UserDto;
import com.example.demo.services.user.UserService;

@Controller
@RequestMapping("/user")
public class ProfileController {

    @Autowired
    private UserService userService;



    @GetMapping("/profile")
    public String getProfile(Model model, Authentication authentication) {
        // Get the username of the currently logged-in user
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();

        // Fetch the user details from the service using the username
        User user = userService.findByUsername(username);  // Changed to findByUsername
        if (user != null) {
            model.addAttribute("user", user);
        }

        // Return the profile view
        return "user_profile/profile";
    }

    @GetMapping("/update")
    public String updateProfile(Model model, Authentication authentication) {
        // Get the email of the currently logged-in user
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        // Fetch the user details from the service
        User user = userService.findByUsername(username);
        if (user != null) {
            // Create a UserDto from the User entity
            UserDto userDto = new UserDto(user.getUsername(), "", user.getFirstname(), user.getLastname(), user.getEmail());
            model.addAttribute("userDto", userDto);
        }

        // Return the update profile view
        return "user_profile/update";  // Refers to the Thymeleaf template file
    }
    @PostMapping("/update")
    public String updateProfile(UserDto userDto, Authentication authentication, Model model) {
        String currentEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User currentUser = userService.findByUsername(currentEmail);        // Update first name and last name
        currentUser.setFirstname(userDto.getFirstname());
        currentUser.setLastname(userDto.getLastname());
        // Print first name and last name to console
        System.out.println("First Name: " + userDto.getFirstname());
        System.out.println("Last Name: " + userDto.getLastname());
        // Handle email update (if different from current)
        if (!userDto.getEmail().equals(currentUser.getEmail())) {
            currentUser.setEmail(userDto.getEmail());
            return "redirect:/logout";  // Redirect to logout
        }
        // Handle password update
    if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            model.addAttribute("errorMessage", "Passwords do not match.");
            return "user_profile/update";
        }
        // Encode and update password
        currentUser.setPassword(userDto.getPassword()); // Directly set the password, no encoding here
    }
    // Save updated user
    userService.saveUser(currentUser);
        // Redirect to profile page
        return "redirect:/user/profile";
    }
}

