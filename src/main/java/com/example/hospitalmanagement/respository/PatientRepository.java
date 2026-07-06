package com.example.hospitalmanagement.respository;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.query.Page;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hospitalmanagement.dto.BloodGroupCountResponseEntity;
import com.example.hospitalmanagement.entity.Patient;
import com.example.hospitalmanagement.entity.type.BloodGroupType;

import jakarta.transaction.Transactional;

public interface PatientRepository extends JpaRepository<Patient, Long>{
    Patient findByName(String name);
    List<Patient> findByBirthDateOrEmail(LocalDate birthDate, String email);

    List<Patient> findByBirthDateBetween(LocalDate startDate, LocalDate endDate);

    List<Patient> findByNameContainingOrderByIdDesc(String query);

    @Query("SELECT p FROM Patient p where p.bloodGroup = ?1")
    List<Patient> findByBloodGroup(@Param("bloodGroup") BloodGroupType bloodGroup);

    @Query("select p from Patient p where p.birthDate > :birthDate")
    List<Patient> findByBornAfterDate(@Param("birthDate") LocalDate birthDate);

    @Query("select new com.example.hospitalmanagement.dto.BloodGroupCountResponseEntity(p.bloodGroup," +
            " Count(p)) from Patient p group by p.bloodGroup")
//    List<Object[]> countEachBloodGroupType();
    List<BloodGroupCountResponseEntity> countEachBloodGroupType();

    @Query(value = "select * from patient", nativeQuery = true)
    Page findAllPatients(Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Patient p SET p.name = :name where p.id = :id")
    int updateNameWithId(@Param("name") String name, @Param("id") Long id);

    @Transactional
    // @Query("select p from Patient p left join fetch p.appointments") // More optimized
    @Query("select p from Patient p left join fetch p.appointments a left join fetch a.doctor") // Unoptimized as it takes more time
    List<Patient> findAllPatientsWithAppointments();

}
