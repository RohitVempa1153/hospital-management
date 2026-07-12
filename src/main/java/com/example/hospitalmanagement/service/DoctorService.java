package com.example.hospitalmanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.hospitalmanagement.dto.DoctorResponseDto;
import com.example.hospitalmanagement.respository.DoctorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public List<DoctorResponseDto> getAllDoctors()
    {
        var doctors = doctorRepository.findAll();
        return doctors.stream().map(doctor -> new DoctorResponseDto(doctor.getId(), doctor.getName(), doctor.getSpecialization(), doctor.getEmail()))
        .collect(Collectors.toList());
    }
}
