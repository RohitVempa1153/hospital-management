package com.example.hospitalmanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.hospitalmanagement.dto.DoctorResponseDto;
import com.example.hospitalmanagement.dto.OnBoardDoctorRequestDto;
import com.example.hospitalmanagement.entity.Doctor;
import com.example.hospitalmanagement.entity.User;
import com.example.hospitalmanagement.entity.type.RoleType;
import com.example.hospitalmanagement.respository.DoctorRepository;
import com.example.hospitalmanagement.respository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final ModelMapper modelMapper;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public List<DoctorResponseDto> getAllDoctors()
    {
        var doctors = doctorRepository.findAll();
        return doctors.stream().map(doctor -> new DoctorResponseDto(doctor.getId(), doctor.getName(), doctor.getSpecialization(), doctor.getEmail()))
        .collect(Collectors.toList());
    }

    @Transactional
    public DoctorResponseDto onBoardNewDoctor(OnBoardDoctorRequestDto request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(()-> new IllegalArgumentException("User with id doesn't exist"));

        if (doctorRepository.existsById(request.getUserId())) {
            throw new IllegalArgumentException("Doctor already exists with userId");
        }

        Doctor doctor = Doctor.builder()
        .user(user)
        .name(request.getName())
        .specialization(request.getSpecialization())
        .build();

        user.getRoles().add(RoleType.DOCTOR);


        return modelMapper.map(doctorRepository.save(doctor), DoctorResponseDto.class);
    }
}
