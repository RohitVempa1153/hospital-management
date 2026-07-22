package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.service.DoctorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospitalmanagement.dto.AppointmentResponseDto;
import com.example.hospitalmanagement.dto.DoctorResponseDto;
import com.example.hospitalmanagement.dto.OnBoardDoctorRequestDto;
import com.example.hospitalmanagement.entity.User;
import com.example.hospitalmanagement.entity.type.RoleType;
import com.example.hospitalmanagement.service.AppointmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
@Slf4j
public class DoctorController {

    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointmentsOfDoctor() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.getRoles().contains(RoleType.DOCTOR)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var response = appointmentService.getAllAppointmentsOfDoctor(user.getId());
        if (response != null && !response.isEmpty()) {
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.noContent().build();
    } 

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("onBoardNewDoctor")
    public ResponseEntity<DoctorResponseDto> onBoardNewDoctor(@RequestBody OnBoardDoctorRequestDto request) throws AccessDeniedException{
        return ResponseEntity.ok(doctorService.onBoardNewDoctor(request));
    }
    
}
