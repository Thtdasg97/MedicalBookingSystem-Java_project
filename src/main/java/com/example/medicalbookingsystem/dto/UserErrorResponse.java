package com.example.medicalbookingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
