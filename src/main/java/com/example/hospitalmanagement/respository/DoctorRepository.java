package com.example.hospitalmanagement.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hospitalmanagement.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{
    
}
