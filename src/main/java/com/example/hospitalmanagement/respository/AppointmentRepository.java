package com.example.hospitalmanagement.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hospitalmanagement.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
    
}
