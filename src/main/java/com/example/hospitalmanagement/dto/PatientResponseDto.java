package com.example.hospitalmanagement.dto;

import java.time.LocalDate;

import com.example.hospitalmanagement.entity.type.BloodGroupType;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PatientResponseDto {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String email;
    private String gender;
    private BloodGroupType bloodGroup;

    public PatientResponseDto(Long id, String name, LocalDate birthDate, String email, String gender, BloodGroupType bloodGroup)
    {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
    }
}
