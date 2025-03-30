package com.example.demo.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.example.demo.models.Dto.user.UserDto;
import com.example.demo.repositories.UserRepository;
@Service
public class UserService {

    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public void registerUser(UserDto userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
         // Assign role based on email domain
         if (userDTO.getEmail().endsWith("@admin.com")) {
            user.setRole("super_user");
        } else if (userDTO.getEmail().endsWith("@responsable.com")) {
            user.setRole("responsable_user");
        } else {
            user.setRole("normal_user");
        }
        
        
        userRepository.save(user);
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);  // Ensure repository method exists
    }
    
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
    public void saveUser(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }
    

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username); // Return null if user is not found
    }
    
}