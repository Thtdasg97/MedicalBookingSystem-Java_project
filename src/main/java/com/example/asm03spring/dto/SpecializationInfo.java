package com.example.asm03spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpecializationInfo {
    private String specializationName;
    private String specializationDescription;
    private Long totalBooking;
    private String clinicName;
}
