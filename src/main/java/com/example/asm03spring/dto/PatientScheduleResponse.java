package com.example.asm03spring.dto;

import com.example.asm03spring.dto.AppointmentDTO;
import com.example.asm03spring.entity.Appointment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientScheduleResponse {
    String message;
    List<AppointmentDTO> appointmentDetail;
}
