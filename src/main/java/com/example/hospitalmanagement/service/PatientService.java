package com.example.hospitalmanagement.service;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.hospitalmanagement.dto.PatientResponseDto;
import com.example.hospitalmanagement.entity.Patient;
import com.example.hospitalmanagement.respository.PatientRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

//    private final EntityManager entityManager;

    @Transactional
    public PatientResponseDto getPatientById(Long id) {

        Patient patient = patientRepository.findById(id).orElseThrow();
        return new PatientResponseDto(patient.getId(), patient.getName(), patient.getBirthDate(), patient.getEmail(), patient.getGender(), patient.getBloodGroup());
    }

    public List<PatientResponseDto> getAllPatients(Integer pageNumber, Integer size)
    {
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<Patient> patients = patientRepository.findAllPatients(pageable);
        List<PatientResponseDto> respones = patients.stream().map(patient -> new PatientResponseDto(patient.getId(), patient.getName(), patient.getBirthDate(), patient.getEmail(), patient.getGender(), patient.getBloodGroup())).collect(Collectors.toList());
        return respones;
    }

    @Transactional
    public void deletePatientById(Long id)
    {
        patientRepository.deleteById(id);
    }
}
