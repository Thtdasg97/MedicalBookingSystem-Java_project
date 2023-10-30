package com.example.asm03spring.service;

import com.example.asm03spring.dao.DoctorInfoRepository;
import com.example.asm03spring.dto.DoctorInfoDTO;
import com.example.asm03spring.entity.DoctorInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorInfoService {
    private final DoctorInfoRepository doctorInfoRepository;

    public void save(DoctorInfo doctorInfo) {
        doctorInfoRepository.save(doctorInfo);
    }

}
