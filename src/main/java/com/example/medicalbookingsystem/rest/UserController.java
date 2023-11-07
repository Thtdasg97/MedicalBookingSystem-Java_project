package com.example.medicalbookingsystem.rest;
import com.example.medicalbookingsystem.dto.*;
import com.example.medicalbookingsystem.entity.Appointment;
import com.example.medicalbookingsystem.entity.Patient;
import com.example.medicalbookingsystem.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final SpecializationService specializationService;
    private final ClinicService clinicService;
    private final UserService userService;
    private final PatientService patientService;
    private final AppointmentServiceImpl appointmentServiceImpl;

    // method use to display top 3 prominent specialities based on sum booking
    @GetMapping("/specializations/prominent-specialties")
    public ResponseEntity<ProminentSpecialtyResponse> getProminentSpecialties() {
        ProminentSpecialtyResponse response = specializationService.getProminentSpecialties();
        return ResponseEntity.ok(response);
    }
    // method use to display top 3 medical facilities based on doctor total
    @GetMapping("/clinics/top-medical-facilities")
    public ResponseEntity<ProminentMedicalFacilityResponse> getTop3MedicalFacilities() {
        ProminentMedicalFacilityResponse response = clinicService.getTop3ProminentClinics();
        return ResponseEntity.ok(response);
    }

    // method use to display user information
    @GetMapping("/{userId}")
    public ResponseEntity<PersonalInformationResponse> getPersonalInformation(@PathVariable int userId) {
        PersonalInformationResponse response = userService.getPersonalInformation(userId);
        return ResponseEntity.ok(response);
    }

    // method use to update user information
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updatePersonalInformation(
            @PathVariable int userId,
            @RequestBody UserUpdateRequest updateRequest) {
        ApiResponse response = userService.updatePersonalInformation(userId, updateRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/doctors-specializations")
    public ResponseEntity<SearchingBySpecializationsResponse> searchSpecializations(
            @RequestParam("keyword") String keyword
    ) {
        SearchingBySpecializationsResponse response = specializationService.searchSpecializations(keyword);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/booking-appointments")
    public ResponseEntity<MakeAppointmentResponse> responseAppointment (
            @RequestBody MakeAppointmentRequest request
    ) {
        return ResponseEntity.ok(appointmentServiceImpl.makeAppointmentResponse(request));
    }


    @GetMapping("/clinics/area")
    public ResponseEntity<SearchResponse> searchClinics(
            @RequestParam("keyword") String keyword
    ) {
        SearchResponse response = clinicService.findAllClinicsByAreaKeyword(keyword);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/clinics/name")
    public ResponseEntity<SearchResponse> getClinicByClinicNameKeyword(
            @RequestParam("keyword") String keyword
    ) {
        SearchResponse result =
                clinicService.findAllClinicsByClinicNameKeyword(keyword);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/clinics/price")
    public ResponseEntity<SearchResponse> getClinicByPriceKeyword(
            @RequestParam("keyword") String keyword
    ) {
        SearchResponse result =
                clinicService.findAllClinicsByPriceKeyword(keyword);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/clinics/category")
    public ResponseEntity<SearchResponse> getClinicByCategoryKeyword(
            @RequestParam("keyword") String keyword
    ) {
        SearchResponse result =
                clinicService.findClinicsByCategoryKeyword(keyword);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/patients/{patientId}")
    public List<Appointment> appointmentList (@PathVariable int patientId) {
        Patient patient = patientService.findPatientById(patientId);
        List<Appointment> appointments = patient.getAppointments();
        return appointments;
    }




}
