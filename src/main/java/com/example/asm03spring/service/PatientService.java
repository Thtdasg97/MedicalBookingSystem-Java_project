package com.example.asm03spring.service;
import com.example.asm03spring.dao.PatientRepository;
import com.example.asm03spring.dto.PathologyDTO;
import com.example.asm03spring.dto.PatientInfoDTO;
import com.example.asm03spring.entity.DoctorUser;
import com.example.asm03spring.entity.Patient;
import com.example.asm03spring.entity.User;
import com.example.asm03spring.exception.PatientsNotFoundException;
import com.example.asm03spring.exception.UserNotFoundException;
import com.example.asm03spring.dto.PatientAccountLockRequest;
import com.example.asm03spring.dto.PatientAccountLockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final DoctorUserService doctorUserService;
    public List<Patient> getPatientsByDoctorId(Integer doctorId) {
        return patientRepository.findListOfPatientByDoctorId(doctorId);
    }

    public List<PatientInfoDTO> getListPatientsByDoctorId(Integer doctorId) {
        DoctorUser doctorUser = doctorUserService.findDoctorById(doctorId);
        if (doctorUser == null) {
            throw new UserNotFoundException("Doctor not found with ID: " + doctorId);
        }
        List<Patient> list = doctorUser.getPatientList();

        if (list.isEmpty()) {
            throw new PatientsNotFoundException("No patients found for doctor with ID: " + doctorId);
        }

        return list.stream().map(this::convertPatientInfo).collect(Collectors.toList());
    }

    public Patient findPatientById(int theId) {
        Optional<Patient> result = patientRepository.findById(theId);
        Patient theUser = null;

        if (result.isPresent()) {
            theUser = result.get();
        } else {
            throw new UserNotFoundException("Did not find the doctor have id - " + theId);
        }
        return theUser;
    }

    private PatientInfoDTO convertPatientInfo(Patient patient) {
        List<PathologyDTO> pathologyDTOList = new ArrayList<>();
        String disease = patient.getMedicalInformation().getBasicDisease();
        String description = patient.getMedicalInformation().getDetailedDescription();
        PathologyDTO pathologyDTO = PathologyDTO
                .builder()
                .description(description)
                .disease(disease)
                .build();
        pathologyDTOList.add(pathologyDTO);

        return PatientInfoDTO
                .builder()
                .fullName(patient.getUser().getName())
                .gender(patient.getUser().getGender())
                .address(patient.getUser().getAddress())
                .pathologyList(pathologyDTOList)
                .build();
    }

    public PatientAccountLockResponse performAccountLockAction(PatientAccountLockRequest request) {
        Patient patient = patientRepository.findByPatientId(request.getPatientId());
        if(patient == null) {
            throw new UserNotFoundException("The patient with id - " + request.getPatientId() + " not found");
        }
        int newStatusId = request.getStatus() == 2 ? 2 : 1;

        patient.setStatusId(newStatusId);
        patientRepository.save(patient);

        String message = newStatusId == 2 ? "Blocked Successfully" : "Unblocked Successfully";
        String description = newStatusId == 2 ? "The account has not been active for a long time!" : "The account can be used!";

        return PatientAccountLockResponse.builder().message(message).description(description).build();
    }

    public void validateAccountLockRequest(PatientAccountLockRequest request) {
        if (request == null || request.getPatientId() <= 0 || (request.getStatus() != 1 && request.getStatus() != 2)) {
            throw new IllegalArgumentException("Invalid request data");
        }
    }

    public Patient findPatientByPatientId(int theId) {
        return patientRepository.findByPatientId(theId);
    }

    public void save(Patient patient) {
        patientRepository.save(patient);
    }
    public Patient findPatientByUser(User user) {
        return patientRepository.findByUser(user);
    }
}
