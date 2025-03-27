package com.anudip.HMS.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter // Lombok should generate setters, but we'll define them manually as a fallback
@NoArgsConstructor
@AllArgsConstructor
public class PatientMedicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @ManyToOne
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @ManyToOne
    @JoinColumn(name = "assigned_by", nullable = false)
    private User doctor;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private boolean purchased = false;

    // Explicitly defining setter methods in case Lombok is not working
    public void setPatient(User patient) {
        this.patient = patient;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}
