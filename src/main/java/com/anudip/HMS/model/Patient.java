package com.anudip.HMS.model;

import jakarta.persistence.*;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;  // 🔗 Link to User entity

    @Column(nullable = true)
    private String medicalHistory; // Example of patient-specific data

    @Column(nullable = true)
    private String insuranceDetails; // Example of patient-specific data

    // Constructors
    public Patient() {}

    public Patient(User user, String medicalHistory, String insuranceDetails) {
        this.user = user;
        this.medicalHistory = medicalHistory;
        this.insuranceDetails = insuranceDetails;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }

    public String getInsuranceDetails() { return insuranceDetails; }
    public void setInsuranceDetails(String insuranceDetails) { this.insuranceDetails = insuranceDetails; }
}
