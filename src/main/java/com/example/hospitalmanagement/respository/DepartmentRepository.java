package com.example.hospitalmanagement.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hospitalmanagement.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{
    
}
