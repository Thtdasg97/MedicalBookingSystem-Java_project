package com.example.medicalbookingsystem.service;
import com.example.medicalbookingsystem.dao.DoctorUserRepository;
import com.example.medicalbookingsystem.entity.DoctorUser;
import com.example.medicalbookingsystem.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorUserService {
    private final DoctorUserRepository doctorUserRepository;

    public DoctorUser findDoctorById(int theId) {
        Optional<DoctorUser> result = doctorUserRepository.findById(theId);
        DoctorUser theUser = null;

        if(result.isPresent()) {
            theUser = result.get();
        } else {
            throw new UserNotFoundException("Did not find the doctor have id - " + theId);
        }
        return theUser;
    }


    public void save(DoctorUser doctorUser) {
        doctorUserRepository.save(doctorUser);
    }

    public DoctorUser findDoctorByDoctorId(int theId) {
        return doctorUserRepository.findDoctorByDoctorId(theId);
    }


}
