package com.example.asm03spring.rest;

import com.example.asm03spring.dto.*;
import com.example.asm03spring.service.AppointmentServiceImpl;
import com.example.asm03spring.service.MedicalInformationService;
import com.example.asm03spring.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final PatientService patientService;
    private final AppointmentServiceImpl appointmentService;
    private final MedicalInformationService medicalInformationService;

    @GetMapping("/{doctorId}/patients")
    public ResponseEntity<List<PatientInfoDTO>> getListPatientInformation(
            @PathVariable Integer doctorId
    ) {
        List<PatientInfoDTO> list = patientService.getListPatientsByDoctorId(doctorId);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/schedule/confirm-appointment")
    public ResponseEntity<ConfirmDoctorResponse> confirmFromDoctor(
            @RequestBody ConfirmDoctorRequest request
    ) {
        ConfirmDoctorResponse response = appointmentService.confirmDoctorResponse(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/medical-information")
    public ResponseEntity<SendMedicalInformationResponse> responseSendMedicalInformation (
            @RequestBody SendMedicalInformationRequest request
    ) {
        SendMedicalInformationResponse response = medicalInformationService.responseMedicalInformation(request);
        return ResponseEntity.ok(response);
    }


}
