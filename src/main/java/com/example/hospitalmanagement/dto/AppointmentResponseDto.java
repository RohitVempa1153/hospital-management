package com.example.hospitalmanagement.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.hospitalmanagement.entity.type.BloodGroupType;

import lombok.Data;

@Data
public class AppointmentResponseDto {
    private Long id;
    private LocalDateTime appointmentTime;
    private String reason;
    private String status;

    public AppointmentResponseDto(Long id, LocalDateTime appointmentTime, String reason, String status)
    {
        this.id = id;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
        this.status = status;
    }
}
