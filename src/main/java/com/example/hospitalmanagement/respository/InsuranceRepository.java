package com.example.hospitalmanagement.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hospitalmanagement.entity.Insurance;

public interface InsuranceRepository extends JpaRepository<Insurance, Long>{
    
}
