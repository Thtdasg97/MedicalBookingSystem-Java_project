package com.example.asm03spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ConfirmResetPasswordErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
