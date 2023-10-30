package com.example.asm03spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorUserDTO {
    String generalIntroduction;
    String trainingProcess;
    String achievements;
    Integer specializationId;
    Integer clinicId;
}
