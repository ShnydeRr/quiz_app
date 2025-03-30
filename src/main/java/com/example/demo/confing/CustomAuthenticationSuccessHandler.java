package com.example.demo.confing;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username);
        
        if (user != null) {
            String email = user.getEmail();
            if (email != null && email.endsWith("@admin.com")) {
                response.sendRedirect("/admin/profile");
            } // Check if the user is a responsable
            else if (email != null && email.endsWith("@responsable.com")) {
                response.sendRedirect("/responsable/profile");
            }
                else {
                    response.sendRedirect("/user/profile");
                }
        } else {
            response.sendRedirect("/login?error");
        }
    }

}