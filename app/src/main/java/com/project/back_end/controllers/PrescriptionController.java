package com.project.back_end.controllers;

import com.project.back_end.models.Prescription;
import com.project.back_end.repo.PrescriptionRepository;
import com.project.back_end.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private TokenService tokenService;

    // POST endpoint that saves a prescription; validates token and validates the request body
    @PostMapping("/save/{token}")
    public ResponseEntity<Map<String, Object>> savePrescription(
            @PathVariable String token,
            @Valid @RequestBody Prescription prescription) {

        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "Invalid or expired token"));
        }

        Prescription saved = prescriptionRepository.save(prescription);
        // Returns a structured success message using ResponseEntity
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("success", true, "prescription", saved));
    }
}
