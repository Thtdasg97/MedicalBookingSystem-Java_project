package com.example.asm03spring.dao;
import com.example.asm03spring.entity.Clinic;
import com.example.asm03spring.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpecializationRepository extends JpaRepository<Specialization, Integer>, JpaSpecificationExecutor<Clinic> {
    @Query(value = "SELECT \n" +
            "    s.name AS specialization_name,\n" +
            "    s.description AS specialization_description,\n" +
            "    SUM(sc.sum_booking) AS total_booking,\n" +
            "    cli.name AS clinic_name\n" +
            "FROM\n" +
            "    specialization s\n" +
            "        JOIN\n" +
            "    doctor_user du ON s.id = du.specialization_id\n" +
            "        JOIN\n" +
            "    schedule sc ON du.doctor_id = sc.doctor_id\n" +
            "        JOIN\n" +
            "    clinic cli ON cli.id = du.clinic_id\n" +
            "GROUP BY s.id, s.name, s.description, cli.name\n" +
            "ORDER BY total_booking DESC\n" +
            "LIMIT 3;", nativeQuery = true)
    List<Object> findTop3SpecializationsByTotalBooking();
    List<Specialization> findByNameContainingIgnoreCase (String keyword);
}
