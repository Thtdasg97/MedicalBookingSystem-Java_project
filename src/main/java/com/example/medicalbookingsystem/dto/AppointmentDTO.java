package com.example.medicalbookingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentDTO {
    String timeBooking;
    String dateBooking;
    int status;
}
