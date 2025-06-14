package com.example.demo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = users.stream().map(UserDTO::mapToDTO).toList();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Users fetched successfully");
        response.put("users", userDTOs);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return new ResponseEntity<>(
                    new UserResponse("User with this email already exists", null, null),
                    HttpStatus.CONFLICT);
        }

        if (userRepository.existsByMobile(user.getMobile())) {
            return new ResponseEntity<>(
                    new UserResponse("User with this mobile number already exists", null, null),
                    HttpStatus.CONFLICT);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        return new ResponseEntity<>(
                new UserResponse("User registration successful", UserDTO.mapToDTO(savedUser)),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
        }

        user.setLastLogin(new Date());
        user.setDeviceToken(loginRequest.getDeviceToken());
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());

        return new ResponseEntity<>(
                new UserResponse("Login successful", UserDTO.mapToDTO(user), token),
                HttpStatus.OK);
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<?> getUserProfile(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(UserDTO.mapToDTO(user));
    }

    @PostMapping("/{id}/accept")
    public String acceptUser(@PathVariable Long id) {
        return "User with ID " + id + " accepted.";
    }

    @PostMapping("/{id}/reject")
    public String rejectUser(@PathVariable Long id) {
        return "User with ID " + id + " rejected.";
    }
}
