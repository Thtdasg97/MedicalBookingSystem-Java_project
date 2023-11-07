package com.example.medicalbookingsystem.service;

import com.example.medicalbookingsystem.dao.DoctorInfoRepository;
import com.example.medicalbookingsystem.entity.DoctorInfo;
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
