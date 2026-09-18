package com.project.back_end.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Prescription - stored in MongoDB (NoSQL), one doc per prescription issued
 * for a completed appointment.
 */
@Document(collection = "prescriptions")
public class Prescription {

    @Id
    private String id;

    @NotNull
    private Long appointmentId;

    @NotNull
    private String patientName;

    @NotNull
    private List<String> medications;

    @Size(max = 500)
    private String doctorNotes;

    public Prescription() {}

    public Prescription(Long appointmentId, String patientName, List<String> medications, String doctorNotes) {
        this.appointmentId = appointmentId;
        this.patientName = patientName;
        this.medications = medications;
        this.doctorNotes = doctorNotes;
    }

    public String getId() { return id; }
    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public List<String> getMedications() { return medications; }
    public void setMedications(List<String> medications) { this.medications = medications; }
    public String getDoctorNotes() { return doctorNotes; }
    public void setDoctorNotes(String doctorNotes) { this.doctorNotes = doctorNotes; }
}
