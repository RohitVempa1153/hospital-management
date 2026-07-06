package com.example.hospitalmanagement.service;

import org.springframework.stereotype.Service;

import com.example.hospitalmanagement.entity.Insurance;
import com.example.hospitalmanagement.entity.Patient;
// import com.example.hospitalmanagement.respository.InsuranceRepository;
import com.example.hospitalmanagement.respository.PatientRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InsuranceService {
    // private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Patient assignInsuranceToPatient(Insurance insurance, Long patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new EntityNotFoundException("Patient not found with id:" + patientId));
        
        patient.setInsurance(insurance);

        return patient;
        
    }

    @Transactional
    public Patient dissociateInsuranceFromPatient(Long patientId)
    {
        Patient patient = patientRepository.findById(patientId).orElseThrow();
        patient.setInsurance(null);
        return patient;
    }
}
