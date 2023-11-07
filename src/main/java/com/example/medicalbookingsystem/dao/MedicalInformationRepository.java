package com.example.medicalbookingsystem.dao;

import com.example.medicalbookingsystem.entity.MedicalInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalInformationRepository extends JpaRepository<MedicalInformation, Integer> {
}
