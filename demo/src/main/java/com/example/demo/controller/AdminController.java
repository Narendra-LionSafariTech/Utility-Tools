package com.example.demo.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdminResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;
import com.example.demo.util.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminController {

    private AdminRepository adminRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register Admin
    @PostMapping
    public ResponseEntity<AdminResponse> createAdmin(@Valid @RequestBody Admin admin) {
        // Hash the password
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        Admin savedAdmin = adminRepository.save(admin);

        AdminResponse response = new AdminResponse("Admin registration successful", savedAdmin);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Admin Login
    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody LoginRequest loginRequest) {
        Optional<Admin> adminOpt = adminRepository.findByEmail(loginRequest.getEmail());

        if (adminOpt.isEmpty()
                || !passwordEncoder.matches(loginRequest.getPassword(), adminOpt.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        Admin admin = adminOpt.get();
        admin.setLastLogin(new Date());
        admin.setDeviceToken(loginRequest.getDeviceToken());
        adminRepository.save(admin);

        String token = jwtUtil.generateToken(admin.getEmail());

        return ResponseEntity.ok(new AdminResponse("Login successful", admin));
    }

}
