package com.example.medicalbookingsystem.dao;

import com.example.medicalbookingsystem.entity.Patient;
import com.example.medicalbookingsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    @Query(value = "select p.* from patient p where doctor_id = :data", nativeQuery = true)
    List<Patient> findListOfPatientByDoctorId(@Param("data") Integer doctorId);
    @Query(value = "select p.* from patient p where patient_id = :data", nativeQuery = true)
    Patient findByPatientId(@Param("data") Integer patientId);
    Optional<Patient> findById(int theId);
    Patient findByUser(User user);

}
