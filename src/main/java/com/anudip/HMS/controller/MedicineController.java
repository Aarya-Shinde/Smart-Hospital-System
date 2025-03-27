package com.anudip.HMS.controller;

import com.anudip.HMS.model.Medicine;
import com.anudip.HMS.model.PatientMedicine;
import com.anudip.HMS.model.User;
import com.anudip.HMS.repository.MedicineRepository;
import com.anudip.HMS.repository.PatientMedicineRepository;
import com.anudip.HMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/medicine")
public class MedicineController {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private PatientMedicineRepository patientMedicineRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/assign")
    public ResponseEntity<?> assignMedicine(
            @RequestParam Long patientId,
            @RequestParam List<Long> medicineIds,
            @RequestParam List<Integer> quantities) {

        Optional<User> patient = userRepository.findById(patientId);
        if (patient.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid Patient ID!");
        }

        if (medicineIds.size() != quantities.size()) {
            return ResponseEntity.badRequest().body("Mismatch in medicines and quantities count!");
        }

        List<PatientMedicine> assignedList = new ArrayList<>();
        for (int i = 0; i < medicineIds.size(); i++) {
            Optional<Medicine> medicine = medicineRepository.findById(medicineIds.get(i));
            if (medicine.isEmpty()) {
                return ResponseEntity.badRequest().body("Medicine ID " + medicineIds.get(i) + " not found!");
            }

            PatientMedicine patientMedicine = new PatientMedicine();
            patientMedicine.setPatient(patient.get());
            patientMedicine.setMedicine(medicine.get());
            patientMedicine.setQuantity(quantities.get(i));

            assignedList.add(patientMedicine);
        }

        patientMedicineRepository.saveAll(assignedList);
        return ResponseEntity.ok("Medicines assigned successfully!");
    }



    // ✅ Purchase assigned medicine (Patient)
    @PutMapping("/purchase")
    public String purchaseMedicine(@RequestParam Long patientMedicineId) {
        Optional<PatientMedicine> patientMedicine = patientMedicineRepository.findById(patientMedicineId);

        if (patientMedicine.isPresent()) {
            PatientMedicine pm = patientMedicine.get();
            if (!pm.isPurchased()) {
                pm.setPurchased(true);
                patientMedicineRepository.save(pm);
                return "Medicine purchased successfully!";
            } else {
                return "Medicine already purchased!";
            }
        }
        return "Assigned medicine not found!";
    }
    // ✅ Get all medicines assigned to a patient
    @GetMapping("/assigned/{patientId}")
    public ResponseEntity<?> getAssignedMedicines(@PathVariable Long patientId) {
        Optional<User> patient = userRepository.findById(patientId);
        if (patient.isEmpty() || !patient.get().getRole().equalsIgnoreCase("PATIENT")) {
            return ResponseEntity.badRequest().body("Invalid patient ID!");
        }

        List<PatientMedicine> assignedMedicines = patientMedicineRepository.findByPatientId(patientId);
        return ResponseEntity.ok(assignedMedicines);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Medicine>> getAllMedicines() {
        return ResponseEntity.ok(medicineRepository.findAll());
    }



    //    frontend can fetch medicine names
    @GetMapping("/assigned/all")
    public ResponseEntity<List<Map<String, Object>>> getAllAssignedMedicines() {
        List<PatientMedicine> assignedList = patientMedicineRepository.findAll();
        List<Map<String, Object>> responseList = new ArrayList<>();

        for (PatientMedicine pm : assignedList) {
            Map<String, Object> map = new HashMap<>();
            map.put("medicineName", pm.getMedicine().getName());
            map.put("patientName", pm.getPatient().getName());
            map.put("quantity", pm.getQuantity());
            responseList.add(map);
        }

        return ResponseEntity.ok(responseList);
    }






}
