package com.example.asm03spring.dto;

import com.example.asm03spring.entity.Role;
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
