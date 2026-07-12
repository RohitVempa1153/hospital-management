package com.example.hospitalmanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospitalmanagement.dto.DoctorResponseDto;
import com.example.hospitalmanagement.service.DoctorService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class HospitalController {
    private final DoctorService doctorService;
    
    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctors() {
        var response = doctorService.getAllDoctors();
        if (response != null && !response.isEmpty()) {
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.noContent().build();
    }
    
}
