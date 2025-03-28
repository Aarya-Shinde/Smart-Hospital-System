package com.anudip.HMS.controller;

import com.anudip.HMS.model.User;
import com.anudip.HMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // ✅ Register User
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (user.getName() == null || user.getName().trim().isEmpty() ||
                user.getEmail() == null || user.getEmail().trim().isEmpty() ||
                user.getPassword() == null || user.getPassword().trim().isEmpty() ||
                user.getRole() == null || user.getRole().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Missing required fields: name, username, password, role.");
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email already registered!");
        }

        user.setRole(user.getRole().toUpperCase());
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully as " + user.getRole());
    }

    //  Login User
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser == null || !user.getPassword().equals(existingUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password!"));
        }

        String role = existingUser.getRole();
        if (role == null || (!role.equalsIgnoreCase("PATIENT") &&
                !role.equalsIgnoreCase("DOCTOR") &&
                !role.equalsIgnoreCase("ADMIN"))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Unknown role!"));
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("role", role);

        return ResponseEntity.ok(response);
    }

    // ✅ Get All Users
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    // ✅ Delete User
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found!");
        }

        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully!");
    }
}
