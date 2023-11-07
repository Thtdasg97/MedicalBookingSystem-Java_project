package com.example.medicalbookingsystem.rest;
import com.example.medicalbookingsystem.dto.*;
import com.example.medicalbookingsystem.service.AppointmentServiceImpl;
import com.example.medicalbookingsystem.service.PatientService;
import com.example.medicalbookingsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final PatientService patientService;
    private final UserService userService;
    private final AppointmentServiceImpl appointmentService;
    @PutMapping("/patients/account")
    public ResponseEntity<PatientAccountLockResponse> lockOrUnlockPatientAccount(
            @RequestBody PatientAccountLockRequest request
    ) {
        try {
            patientService.validateAccountLockRequest(request);

            PatientAccountLockResponse response = patientService.performAccountLockAction(request);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            // Handle bad data or invalid input
            return ResponseEntity.badRequest()
                    .body(new PatientAccountLockResponse("Invalid request data", null));
        }
    }

    @PostMapping("/doctors/account")
    public ResponseEntity<AddDoctorResponse> addNewDoctor(
            @RequestBody AddDoctorRequest request
    ) {
        AddDoctorResponse response = userService.addDoctorResponse(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/doctors/account")
    public ResponseEntity<DoctorAccountLockResponse> lockOrUnlockDoctorAccount(
            @RequestBody DoctorAccountLockRequest request
    ) {
        try {
            userService.validateAccountLockRequest(request);
            DoctorAccountLockResponse response = userService.performAccountLockAction(request);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException ex) {
            // Handle bad data or invalid input
            return ResponseEntity.badRequest()
                    .body(new DoctorAccountLockResponse("Invalid request data", null));

        }
    }

    @GetMapping("/patient-schedule")
    public ResponseEntity<PatientScheduleResponse> viewPatientSchedule(@RequestParam int patientId) {
        try {
            PatientScheduleResponse response = appointmentService.getPatientSchedule(patientId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle exception and return appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PatientScheduleResponse("An error occurred while processing your request.", null));
        }
    }

    @GetMapping("/doctor-schedule")
    public ResponseEntity<DoctorScheduleResponse> viewDoctorSchedule(@RequestParam int doctorId) {
        try {
            DoctorScheduleResponse response = userService.doctorScheduleResponse(doctorId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DoctorScheduleResponse("An error occurred while processing your request",null));
        }
    }



}
