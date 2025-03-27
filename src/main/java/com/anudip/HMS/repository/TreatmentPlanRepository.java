package com.anudip.HMS.repository;

import com.anudip.HMS.model.TreatmentPlan;
import com.anudip.HMS.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentPlanRepository extends JpaRepository<TreatmentPlan, Long> {

    // Find treatment plan by patient
    TreatmentPlan findByPatient(User patient);

    // Find treatment plan by patient ID
    TreatmentPlan findByPatientId(Long patientId);
}
