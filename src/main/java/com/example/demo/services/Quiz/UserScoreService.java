package com.example.demo.services.Quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.example.demo.models.Quiz.UserScore;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.Quiz.UserScoreRepository;


@Service
public class UserScoreService {

    @Autowired
    private UserScoreRepository userScoreRepository;

    @Autowired
    private UserRepository userRepository; // Inject UserRepository to access user data

    public void saveUserScore(Long userId, int score, Long quizId) {
        UserScore userScore = new UserScore(userId, score, quizId); // Include quizId in UserScore
        userScoreRepository.save(userScore);
    }

    public Long getUserIdByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null ? user.getId() : null; // Handle null case if user not found
    }
}
