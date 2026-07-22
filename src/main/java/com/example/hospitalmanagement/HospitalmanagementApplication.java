package com.example.hospitalmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class HospitalmanagementApplication {
 
	public static void main(String[] args) {
		SpringApplication.run(HospitalmanagementApplication.class, args);
		// New project
	}

}
