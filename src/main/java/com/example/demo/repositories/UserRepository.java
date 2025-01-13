package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.User;

public interface UserRepository extends JpaRepository<User, Long> {


    User findByEmail(String email);

    User findByUsername(String username);

    User findByUsernameOrEmail(String username, String email);
    
}