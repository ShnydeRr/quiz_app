package com.example.demo.models.Quiz;


import java.util.ArrayList;
import java.util.List;

import com.example.demo.models.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Quiz")
public class Quiz {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private boolean approved; // Field to track whether the quiz     approved

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions; // A quiz can have multiple questions

    
    @ManyToOne // This defines a many-to-one relationship with the User entity
    @JoinColumn(name = "user_id") // This is the foreign key column
    private User user;
    @ManyToMany
        private List<User> likedByUsers = new ArrayList<>();

    

    public Quiz(Long id, String title, boolean approved, List<Question> questions, User user, List<User> likedByUsers) {
        this.id = id;
        this.title = title;
        this.approved = approved;
        this.questions = questions;
        this.user = user;
        this.likedByUsers = likedByUsers;
    }

    public boolean getApproved() {
        return this.approved;
    }


    public List<User> getLikedByUsers() {
        return this.likedByUsers;
    }

    public void setLikedByUsers(List<User> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }

    public Quiz() {
    }
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}