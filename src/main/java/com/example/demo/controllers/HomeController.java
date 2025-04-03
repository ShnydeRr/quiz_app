package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Serve the home page
    @GetMapping("/")
    public String home() {
        
        return "index"; // Refers to index.html in src/main/resources/templates
    }

    // Serve the contact page
    @GetMapping("/contact")
    public String contact() {
        return "contact"; // Ensure you have a contact.html in src/main/resources/templates
    }

}