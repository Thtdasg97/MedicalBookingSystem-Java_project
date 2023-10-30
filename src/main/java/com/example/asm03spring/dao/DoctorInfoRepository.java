package com.example.asm03spring.dao;

import com.example.asm03spring.entity.DoctorInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorInfoRepository extends JpaRepository<DoctorInfo, Integer> {
}
