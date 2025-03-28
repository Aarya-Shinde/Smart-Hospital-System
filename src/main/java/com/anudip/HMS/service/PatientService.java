package com.anudip.HMS.service;

import com.anudip.HMS.model.Patient;
import com.anudip.HMS.model.User;
import com.anudip.HMS.repository.PatientRepository;
import com.anudip.HMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<Patient> getPatientByUserId(Long userId) {
        return Optional.ofNullable(patientRepository.findByUserId(userId).orElse(null));
    }


    public String createPatient(Long userId, String medicalHistory, String insuranceDetails) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            Patient patient = new Patient(userOptional.get(), medicalHistory, insuranceDetails);
            patientRepository.save(patient);
            return "Patient profile created!";
        } else {
            return "User not found!";
        }
    }

    public String updatePatient(Long userId, Patient updatedPatient) {
        Optional<Patient> patientOptional = patientRepository.findByUserId(userId);

        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();

            // Update Patient-specific fields
            patient.setMedicalHistory(updatedPatient.getMedicalHistory());
            patient.setInsuranceDetails(updatedPatient.getInsuranceDetails());

            // Update User fields (name, email, password)
            User user = patient.getUser();
            if (updatedPatient.getUser().getName() != null) {
                user.setName(updatedPatient.getUser().getName());
            }
            if (updatedPatient.getUser().getEmail() != null) {
                user.setEmail(updatedPatient.getUser().getEmail());
            }
            if (updatedPatient.getUser().getPassword() != null) {
                user.setPassword(updatedPatient.getUser().getPassword()); // Remove passwordEncoder
            }

            // Save changes
            patientRepository.save(patient);
            return "Profile updated successfully!";
        } else {
            throw new RuntimeException("Patient not found!");
        }
    }
}
