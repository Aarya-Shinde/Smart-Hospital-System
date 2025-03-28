package com.anudip.HMS.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // Avoid using "user" as it’s a reserved keyword in SQL
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() {
        return role != null ? role.toUpperCase() : null;
    }

    public void setRole(String role) {
        this.role = (role != null) ? role.toUpperCase() : null;
    }
}
