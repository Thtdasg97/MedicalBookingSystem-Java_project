package com.example.medicalbookingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicalFacilityInfo {
    private String clinicName;
    private String address;
    private String phone;
    private String timeWorking;
    private String description;
    private long totalDoctor;
}
