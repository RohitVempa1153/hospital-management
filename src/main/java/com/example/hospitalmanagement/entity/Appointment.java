package com.example.hospitalmanagement.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Appointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @Column(length = 500)
    private String reason;

    @Column
    private String status;

    // Relations

    @ManyToOne
    @JoinColumn(nullable = false, name = "patient_id") //Patient is required
    private Patient patient;

    @ManyToOne
    @JoinColumn(nullable = false, name = "doctor_id")
    private Doctor doctor;
}
