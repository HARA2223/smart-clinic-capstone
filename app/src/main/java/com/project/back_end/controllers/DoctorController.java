package com.project.back_end.controllers;

import com.project.back_end.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // Exposes a GET endpoint for doctor availability using dynamic parameters
    @GetMapping("/availability/{doctorId}/{date}")
    public ResponseEntity<List<String>> getAvailability(
            @PathVariable Long doctorId,
            @PathVariable String date) {
        List<String> slots = doctorService.getAvailableTimes(doctorId, LocalDate.parse(date));
        return ResponseEntity.ok(slots);
    }

    // Validates the doctor's credentials/token and returns a structured response using ResponseEntity
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        return doctorService.validateLogin(email, password);
    }
}
