package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // Marks this class as a REST API controller
@RequestMapping("/api")  // Optional: Base URL for all endpoints in this class
public class HelloWorldController {

    @GetMapping("/hello")  // Maps HTTP GET requests to this method
    public String sayHello() {
        return "Hello, World!";
    }
}
