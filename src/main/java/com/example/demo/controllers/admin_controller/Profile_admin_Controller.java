package com.example.demo.controllers.admin_controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.example.demo.models.User;
import com.example.demo.models.Dto.user.UserDto;
import com.example.demo.services.admin.adminService;
import com.example.demo.services.user.UserService;

import jakarta.validation.Valid;


//**ddz
@Controller
@RequestMapping("/admin")
public class Profile_admin_Controller {
    @Autowired
    private UserService userService;
    
    @Autowired  
    private adminService adminService;

    @GetMapping("/profile")
    public String getProfile(Model model, Authentication authentication) {
        // Get the email of the currently logged-in user
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        // Fetch the user details from the service
        User user = userService.findByUsername(username);
        if (user != null) {
            model.addAttribute("user", user);
        }
        // Return the profile view
        return "admin_profile/profile";
    }
    @GetMapping("/manage_users")
public String listAllUsers(Model model) {
    // Fetch all users from adminService
    List<User> allUsers = (List<User>) adminService.findAllUsers();
    
    // Filter only users with role "normal_user"
    List<User> filteredUsers = allUsers.stream().filter(user -> "normal_user".equals(user.getRole())).collect(Collectors.toList());

    // Add filtered users to model
    model.addAttribute("users", filteredUsers);

    return "admin_profile/manage_users"; // Refers to manage_users.html
}

    
    
@GetMapping("/manage_responsable")
public String listResponsableUsers(Model model) {
    // Fetch all users from the service
    List<User> allUsers = (List<User>) adminService.findAllUsers();

    // Filter users with role "responsable_user"
    List<User> filteredUsers = allUsers.stream().filter(user -> "responsable_user".equals(user.getRole())).collect(Collectors.toList());

    // Add filtered users to model
    model.addAttribute("users", filteredUsers);

    return "admin_profile/manage_responsable"; // Refers to manage_responsable.html
}

    

    @PostMapping("/delete_users")
    public String deleteUser(@RequestParam("userId") Long userId) {
        User user = adminService.getUserById(userId);
    
        // Ensure the user exists before deletion
        if (user != null) {
            adminService.deleteUserById(userId);
        }
    
        return "redirect:/admin/manage_users";
    }
    
    @PostMapping("/delete_responsable")
    public String deleteResponsable(@RequestParam("userId") Long userId) {
        // Fetch user from the database
        User user = adminService.getUserById(userId);
        
        // Check if user exists and if email ends with "@responsable0"
        if (user != null && user.getEmail().endsWith("@responsable.com")) {
            adminService.deleteUserById(userId);
        }
        
        return "redirect:/admin/manage_responsable";
    }

    

    @GetMapping("/register_responsable")
    public String showRegistrationForm(Model model) {
        // Add an empty UserDto to the model
        model.addAttribute("userDto", new UserDto());
        return "admin_profile/add_responsable"; // Refers to register.html in src/main/resources/templates
    }
    @PostMapping("/register_responsable")
    public String registerUser(@Valid UserDto userDto, BindingResult result, Model model) {
        // Check for validation errors
        if (result.hasErrors()) {
            return "admin_profile/add_responsable"; // Return to the registration form with errors
        }

        // Check if email already exists
        if (userService.emailExists(userDto.getEmail())) {
            result.rejectValue("email", "error.userDto", "An account with this email already exists.");
            return "admin_profile/add_responsable"; // Return to the registration form with error
        }

        // Check if username already exists
        if (userService.usernameExists(userDto.getUsername())) {
            result.rejectValue("username", "error.userDto", "An account with this username already exists.");
            return "admin_profile/add_responsable"; // Return to the registration form with error
        }
        // Register the new user
        userService.registerUser(userDto);

        // Add success message
        model.addAttribute("success", "Registration successful! Please log in.");

        // Redirect to the login page or home page after successful registration
        return "redirect:/admin/manage_responsable";
    }
}