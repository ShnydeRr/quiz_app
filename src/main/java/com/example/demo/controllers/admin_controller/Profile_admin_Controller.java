package com.example.demo.controllers.admin_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.User;
import com.example.demo.services.admin.adminService;
import com.example.demo.services.user.UserService;


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
    public String listUsers(Model model) {
        // Fetch all users from the service
        Iterable<User> users = adminService.findAllUsers();
        model.addAttribute("users", users);
        return "admin_profile/manage_users"; // Refers to manage_users.html in src/main/resources/templates
    }

    @PostMapping("/manage_users/delete")
    public String deleteUser(@RequestParam("userId") Long userId) {
        // Delete user by ID
        adminService.deleteUserById(userId);
        return "redirect:/manage_users";
    }
}