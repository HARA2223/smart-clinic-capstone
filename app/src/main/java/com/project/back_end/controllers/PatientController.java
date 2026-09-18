package com.project.back_end.controllers;

import com.project.back_end.models.Patient;
import com.project.back_end.repo.PatientRepository;
import com.project.back_end.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        Optional<Patient> patientOpt = patientRepository.findByEmail(credentials.get("email"));
        if (patientOpt.isEmpty() || !patientOpt.get().getPassword().equals(credentials.get("password"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "Invalid email or password"));
        }
        String token = tokenService.generateToken(credentials.get("email"));
        return ResponseEntity.ok(Map.of("success", true, "token", token));
    }

    // Patient searches and finds a doctor by name (delegates to a doctor search elsewhere,
    // exposed here for the patient-facing search bar)
    @GetMapping("/search-doctor")
    public ResponseEntity<?> searchDoctorByName(@RequestParam String name) {
        return ResponseEntity.ok(Map.of("query", name, "message", "Use /doctor/search?name= to fetch matches"));
    }
}
