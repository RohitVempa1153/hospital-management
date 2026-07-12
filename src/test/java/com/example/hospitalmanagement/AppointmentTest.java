package com.example.hospitalmanagement;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.hospitalmanagement.entity.Appointment;
import com.example.hospitalmanagement.service.AppointmentService;
import com.example.hospitalmanagement.service.PatientService;


@SpringBootTest
public class AppointmentTest {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

    @Test
    public void appointmentTest()
    {
        Appointment appointment = Appointment.builder()
        .appointmentTime(LocalDateTime.of(2026, 7, 23, 14, 0, 0 ))
        .reason("Sick")
        .build();

        // Appointment newAppointment = appointmentService.createNewAppointment(appointment, 1L, 1L);

        // System.out.println(newAppointment);

        // var updatedAppointment = appointmentService.reAssignAppointmentToAnotherDoctor(newAppointment.getId(), 2L);

        // System.out.println(updatedAppointment);

    }

    @Test
    public void addThreeAppointmentAndDeletePatient()
    {
        Appointment a1 = Appointment.builder()
        .appointmentTime(LocalDateTime.of(2026, 7, 23, 14, 0, 0 ))
        .reason("Sick")
        .build();
        
        Appointment a2 = Appointment.builder()
        .appointmentTime(LocalDateTime.of(2026, 8, 23, 14, 0, 0 ))
        .reason("Cancer")
        .build();

        Appointment a3 = Appointment.builder()
        .appointmentTime(LocalDateTime.of(2026, 9, 23, 14, 0, 0 ))
        .reason("Injury")
        .build();

        // appointmentService.createNewAppointment(a1, 1L, 1L);
        // appointmentService.createNewAppointment(a2, 2L, 1L);
        // appointmentService.createNewAppointment(a3, 2L, 1L);

        patientService.deletePatientById(1L);

    }
}
