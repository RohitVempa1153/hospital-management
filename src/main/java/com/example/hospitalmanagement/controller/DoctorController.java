package com.example.hospitalmanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospitalmanagement.dto.AppointmentResponseDto;
import com.example.hospitalmanagement.service.AppointmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
@Slf4j
public class DoctorController {

    private final AppointmentService appointmentService;
    
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointmentsOfDoctor(
        @RequestParam("id") Long id
    ) {
        var response = appointmentService.getAllAppointmentsOfDoctor(id);
        if (response != null && !response.isEmpty()) {
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.noContent().build();
    }
    
}
