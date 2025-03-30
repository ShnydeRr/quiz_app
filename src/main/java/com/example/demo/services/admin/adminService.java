package com.example.demo.services.admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;


@Service
public class adminService {
    @Autowired
    private UserRepository userRepository;

    // Method to find all users
    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    // Method to delete a user by ID
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }
    public boolean userExists(Long userId) {
        return userRepository.existsById(userId);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
    
}