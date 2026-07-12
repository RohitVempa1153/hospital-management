package com.example.hospitalmanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospitalmanagement.dto.AppointmentResponseDto;
import com.example.hospitalmanagement.dto.CreateAppointmentRequestDto;
import com.example.hospitalmanagement.dto.PatientResponseDto;
import com.example.hospitalmanagement.service.AppointmentService;
import com.example.hospitalmanagement.service.PatientService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("patients")
@RequiredArgsConstructor
public class PatientController {
    
    private final AppointmentService appointmentService;
    private final PatientService patientService;
    
    // Create new appointment
    @PostMapping("appointments")
    public ResponseEntity<AppointmentResponseDto> createNewAppointment(@RequestBody CreateAppointmentRequestDto requestDto) {
        var response = appointmentService.createNewAppointment(requestDto);
        if (response != null) {
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.badRequest().build();
    }
    

    // Get patient by id 
    @GetMapping("/profile")
    public ResponseEntity<PatientResponseDto> getPatientById(@RequestParam("id") Long patient_id) {
        var response = patientService.getPatientById(patient_id);
        if (response != null) {
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.notFound().build();
    }
    
}
