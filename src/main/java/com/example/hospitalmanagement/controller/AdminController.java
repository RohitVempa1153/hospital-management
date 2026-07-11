package com.example.hospitalmanagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospitalmanagement.dto.PatientResponseDto;
import com.example.hospitalmanagement.service.PatientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PatientService patientService;
    
    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(
        @RequestParam("page") Integer pageNumber,
        @RequestParam("size") Integer size
    )
    {
        var response = patientService.getAllPatients(pageNumber, size);
        if (response != null && !response.isEmpty()) {
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.noContent().build();
    }
}
