package com.example.medicalbookingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ForgottenPasswordErrorResponse {
    private int status;
    private String message;
    private long timeStamp;

}
