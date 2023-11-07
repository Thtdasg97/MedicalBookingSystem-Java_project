package com.example.medicalbookingsystem.service;

import com.example.medicalbookingsystem.dao.*;
import com.example.medicalbookingsystem.dto.*;
import com.example.medicalbookingsystem.entity.*;
import com.example.medicalbookingsystem.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final DoctorUserService doctorUserService;
    private final DoctorInfoService doctorInfoService;
    private final SpecializationService specializationService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ClinicService clinicService;

    public List<User> getListUser() {
        return userRepository.findAll();
    }

    public User getUserById(int userId) {
        Optional<User> result = userRepository.findById(userId);
        User theUser = null;

        if(result.isPresent()) {
            theUser = result.get();
        } else {
            throw new UserNotFoundException("Did not find user - " + userId);
        }
        return theUser;
    }

    public void save(User theUser) {
        userRepository.save(theUser);
    }

    public PersonalInformationResponse getPersonalInformation(int userId) {
        User user = getUserById(userId);

        UserDTO userDTO = new UserDTO(
                user.getId(),
                user.getName(),
                user.getGender(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getRole().getName(),
                user.getHistoricalMedicine()
        );

        String message = "Personal information retrieved successfully.";

        PersonalInformationResponse response = new PersonalInformationResponse();
        response.setMessage(message);
        response.setUser(userDTO);

        // You can add medical history information to the response if needed

        return response;
    }

    public ApiResponse updatePersonalInformation(int userId, UserUpdateRequest updateRequest) {
        User userToUpdate = getUserById(userId);

        // Update user information based on the updateRequest
        userToUpdate.setName(updateRequest.getName());
        userToUpdate.setGender(updateRequest.getGender());
        userToUpdate.setEmail(updateRequest.getEmail());
        userToUpdate.setPhoneNumber(updateRequest.getPhoneNumber());
        userToUpdate.setAddress(updateRequest.getAddress());

        // Save the updated user information to the database
        userRepository.save(userToUpdate);


        String message = "Personal information updated successfully.";

        return ApiResponse.builder()
                .message(message)
                .build();
    }

    public User findByName(String doctorName) {
        Optional<User> result = userRepository.findByName(doctorName);
        User theUser = null;

        if(result.isPresent()) {
            theUser = result.get();
        } else {
            throw new UserNotFoundException("Did not find doctor - " + doctorName);
        }
        return theUser;
    }

    public User findByEmail(String theEmail) {
        Optional<User> result = userRepository.findByEmail(theEmail);
        User theUser = null;

        if(result.isPresent()) {
            theUser = result.get();
        } else {
            throw new UserNotFoundException("Did not find email - " + theEmail);
        }
        return theUser;
    }

    public AddDoctorResponse addDoctorResponse (AddDoctorRequest request) {

        // create new user entity
        User newUser = new User();
        newUser.setName(request.getUser().getName());
        newUser.setGender(request.getUser().getGender());
        newUser.setEmail(request.getUser().getEmail());
        newUser.setPhoneNumber(request.getUser().getPhoneNumber());
        newUser.setAddress(request.getUser().getAddress());
        newUser.setPassword(passwordEncoder.encode(request.getUser().getPassword()));
//        int roleId = request.getUser().getRole();
        Role role = roleService.findRoleById(1);
        newUser.setRole(role);
        newUser.setName(request.getUser().getName());
        userRepository.save(newUser);

        // create new doctor user entity
        DoctorUser doctorUser = new DoctorUser();
        // set doctor user entity
        doctorUser.setUser(newUser);
        doctorUser.setStatusId(1);
//        doctorUser.setClinic();

        // create new doctor info
        DoctorInfo newDoctorInfo = new DoctorInfo();
        newDoctorInfo.setGeneralIntroduction(request.getDoctorSpecificInfo().getGeneralIntroduction());
        newDoctorInfo.setTrainingProcess(request.getDoctorSpecificInfo().getTrainingProcess());
        newDoctorInfo.setAchievements(request.getDoctorSpecificInfo().getAchievements());
        doctorInfoService.save(newDoctorInfo);

        // Set the doctor's specialization based on the provided specializationId
        Specialization specialization = specializationService.findSpecializationById(
                request.getDoctorSpecificInfo().getSpecializationId()
        );

        Clinic clinic = clinicService.findClinicById(request.getDoctorSpecificInfo().getClinicId());

        // set new doctor info
        doctorUser.setDoctorInfo(newDoctorInfo);
        doctorUser.setSpecialization(specialization);
        doctorUser.setClinic(clinic);
        doctorUserService.save(doctorUser);

        var jwtToken = jwtService.generateToken(newUser);

        return AddDoctorResponse
                .builder()
                .message("Create new Doctor Successfully.")
                .token(jwtToken)
                .build();
    }

    public DoctorAccountLockResponse performAccountLockAction (DoctorAccountLockRequest request) {
        DoctorUser doctorUser = doctorUserService.findDoctorById(request.getDoctorId());

        if(doctorUser == null) {
            throw new UserNotFoundException("The Doctor with id - " + request.getDoctorId() + " not found");
        }
        int newStatusId = request.getStatus() == 2 ? 2 : 1;

        doctorUser.setStatusId(newStatusId);
        doctorUserService.save(doctorUser);

        String message = newStatusId == 2 ? "Blocked Successfully" : "Unblocked Successfully";
        String description = newStatusId == 2 ? "The account has not been active for a long time!" : "The account can be used!";

        return DoctorAccountLockResponse.builder().message(message).description(description).build();

    }

    public void validateAccountLockRequest(DoctorAccountLockRequest request) {
        if (request == null || request.getDoctorId() <= 0
                || (request.getStatus() != 1 && request.getStatus() != 2)) {
            throw new IllegalArgumentException("Invalid request data");
        }
    }

    public DoctorScheduleResponse doctorScheduleResponse (int doctorId) {
        DoctorUser theDoctor = doctorUserService.findDoctorByDoctorId(doctorId);
        List<Schedule> theDoctorListSchedule = theDoctor.getScheduleList();
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

        for(Schedule schedule : theDoctorListSchedule) {
            List<Appointment> appointmentList = schedule.getAppointmentList();
            List<AppointmentDTO> appointmentDTOList = new ArrayList<>();

            for(Appointment appointment: appointmentList) {
                AppointmentDTO appointmentDTO = AppointmentDTO.builder()
                        .timeBooking(appointment.getTimeBooking())
                        .dateBooking(appointment.getDateBooking())
                        .status(appointment.getStatus())
                        .build();

                appointmentDTOList.add(appointmentDTO);
            }

            ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                    .date(schedule.getDate())
                    .appointmentDTOS(appointmentDTOList)
                    .build();

            scheduleDTOList.add(scheduleDTO);
        }
        return DoctorScheduleResponse.builder()
                .message("Access doctor schedule successfully!")
                .doctorScheduleList(scheduleDTOList)
                .build();

    }

    public DoctorUser findDoctorUserByName(String doctorName) {
        User doctorUser = findByName(doctorName);
        return doctorUserService.findDoctorById(doctorUser.getId());
    }

}
