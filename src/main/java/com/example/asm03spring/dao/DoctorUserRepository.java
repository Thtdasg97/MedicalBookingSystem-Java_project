package com.example.asm03spring.dao;
import com.example.asm03spring.entity.DoctorUser;
import com.example.asm03spring.entity.Patient;
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
