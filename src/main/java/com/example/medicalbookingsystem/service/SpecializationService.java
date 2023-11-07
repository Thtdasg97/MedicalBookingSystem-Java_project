package com.example.medicalbookingsystem.service;

import com.example.medicalbookingsystem.dao.SpecializationRepository;
import com.example.medicalbookingsystem.dto.*;
import com.example.medicalbookingsystem.entity.Clinic;
import com.example.medicalbookingsystem.entity.Specialization;
import com.example.medicalbookingsystem.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecializationService {
    private final SpecializationRepository specializationRepository;

    public List<Object> findTop3SpecializationsByTotalBooking() {
        return specializationRepository.findTop3SpecializationsByTotalBooking();
    }

    public ProminentSpecialtyResponse getProminentSpecialties() {
        List<Object> specializationData = findTop3SpecializationsByTotalBooking();
        List<SpecializationInfo> specializationInfos = new ArrayList<>();

        for (Object data : specializationData) {
            Object[] dataArray = (Object[]) data;
            SpecializationInfo specializationInfo = new SpecializationInfo(
                    (String) dataArray[0], // specialization_name
                    (String) dataArray[1], // specialization_description
                    ((Number) dataArray[2]).longValue(), // total_booking
                    (String) dataArray[3] // clinic_name
            );
            specializationInfos.add(specializationInfo);
        }

        String message = "Prominent specialties retrieved successfully.";

        ProminentSpecialtyResponse response = new ProminentSpecialtyResponse();
        response.setMessage(message);
        response.setSpecialties(specializationInfos);

        return response;
    }

    public SearchingBySpecializationsResponse searchSpecializations(String keyword) {
        List<Specialization> specializations = specializationRepository.findByNameContainingIgnoreCase(keyword);

        List<SpecializationInformationDTO> specializationInfoList = specializations.stream()
                .map(this::convertSpecializationInfo)
                .collect(Collectors.toList());

        String message;
        if (specializationInfoList.isEmpty()) {
            throw new UserNotFoundException("No specialization found for the provided keyword: " + keyword);
        } else {
            message = "Search results retrieved successfully.";
        }

        return new SearchingBySpecializationsResponse(message, specializationInfoList);
    }

    private SpecializationInformationDTO convertSpecializationInfo(Specialization specialization) {
        List<DoctorInfoDTO> doctors = specialization.getDoctorUsers().stream()
                .filter(doctorUser -> doctorUser.getUser() != null)
                .map(doctorUser -> {
                    Clinic clinic = doctorUser.getClinic();
                    String clinicName = clinic != null ? clinic.getName() : null;
                    return DoctorInfoDTO.builder()
                            .name(doctorUser.getUser().getName())
                            .clinicName(clinicName)
                            .build();
                })
                .collect(Collectors.toList());

        return SpecializationInformationDTO.builder()
                .name(specialization.getName())
                .description(specialization.getDescription())
                .doctors(doctors)
                .build();
    }
    public Specialization findSpecializationById(Integer specializationId) {
        return specializationRepository.findById(specializationId).
                orElseThrow(() -> new RuntimeException("Specialization not found"));
    }
}
