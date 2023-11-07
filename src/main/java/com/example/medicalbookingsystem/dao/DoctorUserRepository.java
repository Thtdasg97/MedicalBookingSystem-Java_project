package com.example.medicalbookingsystem.dao;
import com.example.medicalbookingsystem.entity.DoctorUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DoctorUserRepository extends JpaRepository<DoctorUser, Integer> {
//    @Query(value = "select p.* from patient p where patient_id = :data", nativeQuery = true)
//    Patient findByPatientId(@Param("data") Integer patientId);
//
//    @Query(value="select du.* from doctor_user du where doctor_id = :data", nativeQuery = true);
    @Query(value = "select du.* from doctor_user du where doctor_id = :data", nativeQuery = true)
    DoctorUser findDoctorByDoctorId(@Param("data") Integer doctorId);

}
