package com.example.medicalbookingsystem.dao;

import com.example.medicalbookingsystem.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findTop3ByOrderBySumBookingDesc();
//    @Query(value = "SELECT SUM(sum_booking) FROM schedule WHERE doctor_id = :data", nativeQuery = true)
//    int findSumBookingByDoctorId(@Param("data") int theId);
//
//    @Query(value = "SELECT MAX(max_booking) FROM schedule WHERE doctor_id = :data", nativeQuery = true)
//    int findMaxBookingByDoctorId(@Param("data") int theId);

}
