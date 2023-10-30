package com.example.asm03spring.dto;

import com.example.asm03spring.dto.DoctorInfoDTO;
import com.example.asm03spring.dto.DoctorUserDTO;
import com.example.asm03spring.dto.NewUserDTO;
import com.example.asm03spring.dto.UserDTO;
import lombok.Data;

@Data
public class AddDoctorRequest {
    private NewUserDTO user;
    private DoctorUserDTO doctorSpecificInfo;
}
