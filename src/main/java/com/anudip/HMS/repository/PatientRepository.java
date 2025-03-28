package com.anudip.HMS.repository;

import com.anudip.HMS.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUserId(Long userId); // Ensure it returns Optional<Patient>
}
