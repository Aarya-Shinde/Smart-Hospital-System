package com.anudip.HMS.model;

import jakarta.persistence.*;

@Entity
public class TreatmentPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private User patient;

    private String planType; // Modern, Traditional, Homeopathy, Ayurvedic

    // Constructors
    public TreatmentPlan() {}

    public TreatmentPlan(User patient, String planType) {
        this.patient = patient;
        this.planType = planType;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }
}
