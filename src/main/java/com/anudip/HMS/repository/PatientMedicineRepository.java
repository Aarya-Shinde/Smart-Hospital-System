package com.anudip.HMS.repository;

import com.anudip.HMS.model.PatientMedicine;
import com.anudip.HMS.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientMedicineRepository extends JpaRepository<PatientMedicine, Long> {
    List<PatientMedicine> findByPatientId(Long patientId);
    List<PatientMedicine> findByPatient(User patient);
}
