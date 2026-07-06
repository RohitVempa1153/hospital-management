package com.example.hospitalmanagement.service;

import org.springframework.stereotype.Service;

import com.example.hospitalmanagement.entity.Appointment;
import com.example.hospitalmanagement.entity.Doctor;
import com.example.hospitalmanagement.entity.Patient;
import com.example.hospitalmanagement.respository.AppointmentRepository;
import com.example.hospitalmanagement.respository.DoctorRepository;
import com.example.hospitalmanagement.respository.PatientRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
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
}
