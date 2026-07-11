package com.example.hospitalmanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.hospitalmanagement.dto.AppointmentResponseDto;
import com.example.hospitalmanagement.entity.Appointment;
import com.example.hospitalmanagement.entity.Doctor;
import com.example.hospitalmanagement.entity.Patient;
import com.example.hospitalmanagement.respository.AppointmentRepository;
import com.example.hospitalmanagement.respository.DoctorRepository;
import com.example.hospitalmanagement.respository.PatientRepository;

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
    public Appointment createNewAppointment(Appointment appointment, Long doctorId, Long patientId){
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        Patient patient = patientRepository.findById(patientId).orElseThrow();

        if(appointment.getId() != null)
            throw new IllegalArgumentException("Appointment should not exist prior");

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        // For bi-directional mapping consistency
        patient.getAppointments().add(appointment);
        doctor.getAppointments().add(appointment);

        appointmentRepository.save(appointment);

        return appointment;
    }

    @Transactional
    public Appointment reAssignAppointmentToAnotherDoctor(Long appointmentId, Long doctorId){
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        appointment.setDoctor(doctor);
        doctor.getAppointments().add(appointment);

        return appointment;
    }

    public List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long doctor_id)
    {
        List<Appointment> appointments = appointmentRepository.findAllByDoctorId(doctor_id);
        return appointments.stream().map(appointment -> new AppointmentResponseDto(appointment.getId(), appointment.getAppointmentTime(), appointment.getReason(), appointment.getStatus()))
        .collect(Collectors.toList());
    }
}
