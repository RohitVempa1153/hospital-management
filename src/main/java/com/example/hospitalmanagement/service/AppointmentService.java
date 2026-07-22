package com.example.hospitalmanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.hospitalmanagement.dto.AppointmentResponseDto;
import com.example.hospitalmanagement.dto.CreateAppointmentRequestDto;
import com.example.hospitalmanagement.entity.Appointment;
import com.example.hospitalmanagement.entity.Doctor;
import com.example.hospitalmanagement.respository.AppointmentRepository;
import com.example.hospitalmanagement.respository.DoctorRepository;
import com.example.hospitalmanagement.respository.PatientRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public AppointmentResponseDto createNewAppointment(CreateAppointmentRequestDto createAppointmentRequestDto)
    {
        var patient = patientRepository.findById(createAppointmentRequestDto.getPatientId())
        .orElseThrow(() -> new EntityNotFoundException("Patient not found with id:" + createAppointmentRequestDto.getPatientId())); 
        var doctor = doctorRepository.findById(createAppointmentRequestDto.getDoctorId())
        .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id:" + createAppointmentRequestDto.getDoctorId()));

        Appointment appointment = Appointment.builder()
        .appointmentTime(createAppointmentRequestDto.getAppointmentTime())
        .reason(createAppointmentRequestDto.getReason())
        .build();

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        // For bidirectional mapping
        patient.getAppointments().add(appointment);
        doctor.getAppointments().add(appointment);

        appointment = appointmentRepository.save(appointment);

        return new AppointmentResponseDto(appointment.getId(), appointment.getAppointmentTime(), appointment.getReason(), appointment.getStatus());
    }



    @Transactional
    public Appointment reAssignAppointmentToAnotherDoctor(Long appointmentId, Long doctorId){
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        appointment.setDoctor(doctor);
        doctor.getAppointments().add(appointment);

        return appointment;
    }

    @PreAuthorize("hasRole('ADMIN') OR (hasRole('DOCTOR') AND #doctor_id == authentication.principal.id)")
    public List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long doctor_id)
    {
        List<Appointment> appointments = appointmentRepository.findAllByDoctorId(doctor_id);
        return appointments.stream().map(appointment -> new AppointmentResponseDto(appointment.getId(), appointment.getAppointmentTime(), appointment.getReason(), appointment.getStatus()))
        .collect(Collectors.toList());
    }
}
