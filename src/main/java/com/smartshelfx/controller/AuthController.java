package com.smartshelfx.controller;

import com.smartshelfx.model.User;
import com.smartshelfx.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.ok("Email already exists!");
        }
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/forgot")
    public ResponseEntity<String> forgotPassword(@RequestBody User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            existingUser.setPassword(user.getPassword());
            userRepository.save(existingUser);
            return ResponseEntity.ok("Password updated successfully!");
        }
        return ResponseEntity.ok("User not found!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok("Login successful!");
        }
        return ResponseEntity.ok("Invalid email or password!");
    }
}
