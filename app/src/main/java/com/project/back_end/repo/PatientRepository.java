package com.project.back_end.repo;

import com.project.back_end.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Retrieves a patient by email using a derived query method
    Optional<Patient> findByEmail(String email);

    // Retrieves a patient using either email or phone number
    Optional<Patient> findByEmailOrPhone(String email, String phone);
}
