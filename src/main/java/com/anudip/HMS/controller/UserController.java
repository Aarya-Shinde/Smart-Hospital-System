package com.anudip.HMS.controller;

import com.anudip.HMS.model.User;
import com.anudip.HMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Map;  // ✅ Add this
import java.util.HashMap;  // ✅ Add this

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists!");
        }

        // Convert role to uppercase and print it
        user.setRole(user.getRole().toUpperCase());
        System.out.println("Registering user with role: " + user.getRole());

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully as " + user.getRole());
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser == null || !user.getPassword().equals(existingUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password!"));
        }

        // Debug: Check retrieved role
        String role = existingUser.getRole();
        System.out.println("🔹 Retrieved role from DB: " + role);

        if (role == null) {
            System.out.println("❌ Role is NULL in login method");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Unknown role!"));
        }

        if (!role.equalsIgnoreCase("PATIENT") &&
                !role.equalsIgnoreCase("DOCTOR") &&
                !role.equalsIgnoreCase("ADMIN")) {
            System.out.println("❌ Role doesn't match expected values!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Unknown role!"));
        }

        System.out.println("✅ Role recognized: " + role);

        // Return JSON response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("role", role);

        return ResponseEntity.ok(response);
    }


}
