package com.example.medicalbookingsystem.dto;

import lombok.Data;

@Data
public class AddDoctorRequest {
    private NewUserDTO user;
    private DoctorUserDTO doctorSpecificInfo;
}
