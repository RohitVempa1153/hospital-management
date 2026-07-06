package com.example.hospitalmanagement;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.hospitalmanagement.entity.Insurance;
import com.example.hospitalmanagement.entity.Patient;
import com.example.hospitalmanagement.service.InsuranceService;

import lombok.RequiredArgsConstructor;

@SpringBootTest
public class InsuranceTest {
    @Autowired
    private InsuranceService insuranceService;

    @Test
    public void testInsurance(){
         
        Insurance insurance = Insurance.builder()
        .policyNumber("HDFC_1234")
        .provider("HDFC")
        .validUntil(LocalDate.of(2030, 12, 12))
        .build();

        Patient p = insuranceService.assignInsuranceToPatient(insurance, 1L);
        System.out.println("Insurance added" +p);

        Patient patient = insuranceService.dissociateInsuranceFromPatient(p.getId());
        System.out.println("Removed insurance" + patient);
    } 

    @Test
    public void test()
    {
        // Adding 3 appointments
        Insurance i1 = Insurance.builder()
        .policyNumber("HDFC_1234")
        .provider("HDFC")
        .validUntil(LocalDate.of(2030, 12, 12))
        .build();
        Insurance i2 = Insurance.builder()
        .policyNumber("SBI_1235")
        .provider("SBI")
        .validUntil(LocalDate.of(2035, 12, 12))
        .build();
        Insurance i3 = Insurance.builder()
        .policyNumber("ICICI_1236")
        .provider("ICICI")
        .validUntil(LocalDate.of(2036, 12, 12))
        .build();

        // Assign those 3 insurances
        insuranceService.assignInsuranceToPatient(i1, 1L);
        insuranceService.assignInsuranceToPatient(i2, 1L);
        var patient = insuranceService.assignInsuranceToPatient(i3, 1L);

        System.out.println(patient);
    }
}
