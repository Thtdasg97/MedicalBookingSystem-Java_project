package com.example.medicalbookingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchingBySpecializationsResponse {
    String message;
    List<SpecializationInformationDTO> specializationInformationDTOS;
}
