package com.example.medicalbookingsystem.dao;

import com.example.medicalbookingsystem.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

}
