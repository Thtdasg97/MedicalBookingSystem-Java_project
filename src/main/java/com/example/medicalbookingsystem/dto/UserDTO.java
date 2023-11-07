package com.example.medicalbookingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String name;
    private String gender;
    private String email;
    private String phoneNumber;
    private String address;
    private String role;
    private String historicalMedicine;
}
