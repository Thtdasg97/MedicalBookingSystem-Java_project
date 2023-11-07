package com.example.medicalbookingsystem.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorAccountLockResponse {
    String message;
    String description;
}
