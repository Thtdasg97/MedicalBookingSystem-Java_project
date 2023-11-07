package com.example.medicalbookingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDTO {
//    private Integer id;
    private String name;
    private String gender;
    private String email;
    private String phoneNumber;
    private String address;
    private String password;
//    private Integer role;
}
