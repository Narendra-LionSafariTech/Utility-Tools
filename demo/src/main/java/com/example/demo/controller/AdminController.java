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

import com.example.demo.dto.AdminDTO;
import com.example.demo.dto.AdminResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;
import com.example.demo.util.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminController {

    private final AdminRepository adminRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AdminController(AdminRepository adminRepository, JwtUtil jwtUtil) {
        this.adminRepository = adminRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<AdminResponse> createAdmin(@Valid @RequestBody Admin admin) {
        Optional<Admin> existing = adminRepository.findByEmail(admin.getEmail());
        if (existing.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AdminResponse("Admin with this email already exists", null));
        }

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        Admin savedAdmin = adminRepository.save(admin);

        AdminDTO adminDTO = new AdminDTO(savedAdmin);
        return new ResponseEntity<>(
                new AdminResponse("Admin registration successful", adminDTO),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody LoginRequest loginRequest) {
        Optional<Admin> adminOpt = adminRepository.findByEmail(loginRequest.getEmail());

        if (adminOpt.isEmpty() || !passwordEncoder.matches(
                loginRequest.getPassword(), adminOpt.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        Admin admin = adminOpt.get();
        admin.setLastLogin(new Date());
        admin.setDeviceToken(loginRequest.getDeviceToken());
        adminRepository.save(admin);

        String token = jwtUtil.generateToken(admin.getEmail());

        AdminDTO adminDTO = new AdminDTO(admin);
        return ResponseEntity.ok(new AdminResponse("Login successful", adminDTO, token));
    }

}
