package com.anudip.HMS.controller;

import com.anudip.HMS.model.Patient;
import com.anudip.HMS.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getPatientProfile(@PathVariable Long userId) {
        Optional<Patient> patient = patientService.getPatientByUserId(userId);
        return patient.map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Patient not found!"));

    }

    @PostMapping("/create")
    public ResponseEntity<String> createPatient(@RequestParam Long userId, @RequestParam String medicalHistory, @RequestParam String insuranceDetails) {
        String message = patientService.createPatient(userId, medicalHistory, insuranceDetails);
        return ResponseEntity.ok(message);
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updatePatientProfile(@PathVariable Long userId, @RequestBody Patient updatedPatient) {
        String message = patientService.updatePatient(userId, updatedPatient);
        return ResponseEntity.ok(message);
    }


}
