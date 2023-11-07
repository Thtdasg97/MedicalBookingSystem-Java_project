package com.example.medicalbookingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClinicsSearchResultDTO {
    private String clinicName;
    private String area;
    private String phone;
    private String timeWorking;
    private String description;
    private String category;
    private String price;
}
