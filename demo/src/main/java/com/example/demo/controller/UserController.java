package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Get all users
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        List<User> users = userRepository.findAll();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Users fetched successfully");
        response.put("users", users);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        UserResponse response = new UserResponse("User registration successful", savedUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();

        // Compare passwords (plaintext check for now — NOT recommended for production)
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(
                new UserResponse("Login successful", user),
                HttpStatus.OK
        );
    }

    // Example: Get user profile by ID
    @GetMapping("/{id}/profile")
    public User getUserProfile(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

// Example: Accept a user account
    @PostMapping("/{id}/accept")
    public String acceptUser(@PathVariable Long id) {
        // Add logic to update status in DB
        return "User with ID " + id + " accepted.";
    }

// Example: Reject a user account
    @PostMapping("/{id}/reject")
    public String rejectUser(@PathVariable Long id) {
        // Add logic to update status in DB
        return "User with ID " + id + " rejected.";
    }

}

// ------------------------------------test case-------------
// // Create new user
// @PostMapping
// public User createUser(@Valid @RequestBody User user) {
//     return userRepository.save(user);
    // }
