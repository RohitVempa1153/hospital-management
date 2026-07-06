package com.example.hospitalmanagement;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.hospitalmanagement.entity.Patient;
import com.example.hospitalmanagement.respository.PatientRepository;
import com.example.hospitalmanagement.service.PatientService;

@SpringBootTest
public class PatientTest {
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void patientTest()
    {
        // List<Patient> patientList = patientRepository.findAll();
        List<Patient> patientList = patientRepository.findAllPatientsWithAppointments();
        System.out.println(patientList);
    }
}
