package com.example.asm03spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
