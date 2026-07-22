package com.example.hospitalmanagement.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
