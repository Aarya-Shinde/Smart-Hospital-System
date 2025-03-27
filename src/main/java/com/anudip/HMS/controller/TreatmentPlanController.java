package com.anudip.HMS.controller;


import com.anudip.HMS.model.TreatmentPlan;
import com.anudip.HMS.model.User;
import com.anudip.HMS.repository.TreatmentPlanRepository;
import com.anudip.HMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/treatment")
public class TreatmentPlanController {

    @Autowired
    private TreatmentPlanRepository treatmentPlanRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Subscribe to a treatment plan (Patient)
    @PostMapping("/subscribe")
    public String subscribeToTreatment(@RequestParam Long patientId, @RequestParam String planType) {
        Optional<User> patient = userRepository.findById(patientId);

        if (patient.isPresent()) {
            TreatmentPlan treatmentPlan = new TreatmentPlan();
            treatmentPlan.setPatient(patient.get());
            treatmentPlan.setPlanType(planType);
            treatmentPlanRepository.save(treatmentPlan);
            return "Successfully subscribed to " + planType + " treatment plan!";
        }
        return "Patient not found!";
    }

    // ✅ Get the current treatment plan of a patient
    @GetMapping("/patient/{patientId}")
    public TreatmentPlan getPatientTreatmentPlan(@PathVariable Long patientId) {
        Optional<User> patient = userRepository.findById(patientId);
        return patient.map(treatmentPlanRepository::findByPatient).orElse(null);
    }

    // ✅ Update treatment plan (Patient)
    @PutMapping("/update")
    public String updateTreatmentPlan(@RequestParam Long patientId, @RequestParam String newPlanType) {
        Optional<TreatmentPlan> treatmentPlan = Optional.ofNullable(treatmentPlanRepository.findByPatient(userRepository.findById(patientId).orElse(null)));

        if (treatmentPlan.isPresent()) {
            treatmentPlan.get().setPlanType(newPlanType);
            treatmentPlanRepository.save(treatmentPlan.get());
            return "Treatment plan updated to " + newPlanType + "!";
        }
        return "No existing treatment plan found!";
    }
    @GetMapping("/getPlan")
    public TreatmentPlan getTreatmentPlan(@RequestParam Long patientId) {
        return treatmentPlanRepository.findByPatientId(patientId);
    }
}
