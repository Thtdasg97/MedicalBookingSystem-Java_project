package com.example.asm03spring.dao;

import com.example.asm03spring.entity.MedicalInformation;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalInformationRepository extends JpaRepository<MedicalInformation, Integer> {
}
