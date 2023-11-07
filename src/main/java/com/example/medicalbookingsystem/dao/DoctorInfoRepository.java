package com.example.medicalbookingsystem.dao;

import com.example.medicalbookingsystem.entity.DoctorInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorInfoRepository extends JpaRepository<DoctorInfo, Integer> {
}
