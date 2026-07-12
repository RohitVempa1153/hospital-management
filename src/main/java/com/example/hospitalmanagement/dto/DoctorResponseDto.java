package com.example.hospitalmanagement.dto;

import lombok.Data;

@Data
public class DoctorResponseDto {
    private Long id;
    private String name;
    private String specialization;
    private String email;

    public DoctorResponseDto(Long id, String name, String specialization, String email)
    {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.email = email;
    }
}
