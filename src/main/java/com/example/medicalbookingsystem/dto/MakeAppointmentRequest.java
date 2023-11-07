package com.example.medicalbookingsystem.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MakeAppointmentRequest {
    private String doctorName;

    private String time;

    private int price;

    private int bookingUserId;
}
