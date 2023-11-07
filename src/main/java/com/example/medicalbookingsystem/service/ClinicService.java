package com.example.medicalbookingsystem.service;

import com.example.medicalbookingsystem.dao.ClinicRepository;
import com.example.medicalbookingsystem.dto.ClinicsSearchResultDTO;
import com.example.medicalbookingsystem.dto.MedicalFacilityInfo;
import com.example.medicalbookingsystem.dto.ProminentMedicalFacilityResponse;
import com.example.medicalbookingsystem.dto.SearchResponse;
import com.example.medicalbookingsystem.entity.Clinic;
import com.example.medicalbookingsystem.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClinicService {
    private final ClinicRepository clinicRepository;

    public List<Object> listTop3ProminentClinic() {
        return clinicRepository.listTop3ProminentClinic();
    }
    public ProminentMedicalFacilityResponse getTop3ProminentClinics() {
        List<Object> clinicData = listTop3ProminentClinic();
        List<MedicalFacilityInfo> medicalFacilityInfos = new ArrayList<>();

        for (Object data : clinicData) {
            Object[] dataArray = (Object[]) data;
            MedicalFacilityInfo medicalFacilityInfo = new MedicalFacilityInfo(
                    (String) dataArray[0], // clinic_name
                    (String) dataArray[1], // address
                    (String) dataArray[2], // phone
                    (String) dataArray[3], // time_working
                    (String) dataArray[4],
                    (long) dataArray[5]// description
            );
            medicalFacilityInfos.add(medicalFacilityInfo);
        }


        String message = "Prominent medical facilities retrieved successfully.";

        ProminentMedicalFacilityResponse response = new ProminentMedicalFacilityResponse();
        response.setMessage(message);
        response.setMedicalFacilities(medicalFacilityInfos);

        return response;
    }

    public Clinic findClinicById(int theId) {
        Optional<Clinic> result = clinicRepository.findById(theId);
        Clinic clinic = null;

        if(result.isPresent()) {
            clinic = result.get();
        } else {
            throw new UserNotFoundException("Don't have clinic with id - " + theId);
        }
        return clinic;
    }

    public SearchResponse findAllClinicsByAreaKeyword(String keyword) {
        List<Clinic> clinicList = clinicRepository.findClinicsByAddressKeyword(keyword);

        if (clinicList.isEmpty()) {
            throw new UserNotFoundException("No clinics found for the provided keyword: " + keyword);
        }

        List<ClinicsSearchResultDTO> clinics = clinicList.stream().map(this::convertClinicsSearchResult).collect(Collectors.toList());
        String message = "Search results retrieved successfully.";

        return new SearchResponse(message, clinics);
    }
    public SearchResponse findAllClinicsByPriceKeyword(String keyword) {
        List<Clinic> clinicList = clinicRepository.findClinicsByPriceKeyword(keyword);

        if (clinicList.isEmpty()) {
            throw new UserNotFoundException("No clinics found for the provided keyword: " + keyword);
        }

        List<ClinicsSearchResultDTO> clinics = clinicList.stream().map(this::convertClinicsSearchResult).collect(Collectors.toList());
        String message = "Search results retrieved successfully.";

        return  new SearchResponse(message, clinics);
    }
    public SearchResponse findAllClinicsByClinicNameKeyword(String keyword) {
        List<Clinic> clinicList = clinicRepository.findClinicsByClinicNameKeyword(keyword);

        if (clinicList.isEmpty()) {
            throw new UserNotFoundException("No clinics found for the provided keyword: " + keyword);
        }

        List<ClinicsSearchResultDTO> clinics = clinicList.stream().map(this::convertClinicsSearchResult).collect(Collectors.toList());
        String message = "Search results retrieved successfully.";

        return new SearchResponse(message, clinics);
    }

    public SearchResponse findClinicsByCategoryKeyword(String keyword) {
        List<Clinic>  clinicList = clinicRepository.findClinicsByCategoryKeyword(keyword);
        if (clinicList.isEmpty()) {
            throw new UserNotFoundException("No clinics found for the provided keyword: " + keyword);
        }

        List<ClinicsSearchResultDTO> clinics = clinicList.stream().map(this::convertClinicsSearchResult).collect(Collectors.toList());
        String message = "Search results retrieved successfully.";

        return new SearchResponse(message, clinics);
    }


    public ClinicsSearchResultDTO convertClinicsSearchResult (Clinic clinic) {
        return ClinicsSearchResultDTO.builder()
                .clinicName(clinic.getName())
                .area(clinic.getAddress())
                .phone(clinic.getPhone())
                .timeWorking(clinic.getTimeWorking())
                .description(clinic.getDescription())
                .price(clinic.getPrice())
                .category(clinic.getCategory().getName())
                .build();
    }



}
