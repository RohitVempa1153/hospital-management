package com.example.hospitalmanagement.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column
    private String specialization;

    @Column(nullable = false, unique = true)
    private String email;

    @CreationTimestamp
    @org.hibernate.annotations.ColumnDefault("CURRENT_TIMESTAMP")
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Relations

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

    @ManyToMany(mappedBy = "doctors")
    private Set<Department> departments = new HashSet<>();
}
