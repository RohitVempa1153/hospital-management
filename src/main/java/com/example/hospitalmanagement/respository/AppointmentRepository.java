package com.example.hospitalmanagement.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hospitalmanagement.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{


    // List<Appointment> findAllByDoctorId(Long id);
    @Query("select a from Appointment a where a.doctor.id = :id")
    List<Appointment> findAllByDoctorId(@Param("id") Long id);
}
