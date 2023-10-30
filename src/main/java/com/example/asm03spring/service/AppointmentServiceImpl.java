package com.example.asm03spring.service;
import com.example.asm03spring.dao.AppointmentRepository;
import com.example.asm03spring.dto.*;
import com.example.asm03spring.entity.*;
import com.example.asm03spring.exception.InvalidAppointmentException;
import com.example.asm03spring.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl {
    private final AppointmentRepository appointmentRepository;
    private final ScheduleService scheduleService;
    private final DoctorUserService doctorUserService;
    private final PatientService patientService;
    private final UserService userService;
    private final RoleService roleService;

    public Appointment findAppointmentById(int theId) {
        Optional<Appointment> result = appointmentRepository.findById(theId);
        Appointment theAppointment = null;

        if(result.isPresent()) {
            theAppointment = result.get();
        } else {
            throw new UserNotFoundException("Did not find the schedule have id - " + theId);
        }
        return theAppointment;
    }
    public ConfirmDoctorResponse confirmDoctorResponse(ConfirmDoctorRequest request) {
        DoctorUser doctorUser = doctorUserService.findDoctorById(request.getDoctorId());
        Schedule specificSchedule = scheduleService.findScheduleById(request.getScheduleId());

        if (!doctorUser.getScheduleList().contains(specificSchedule)) {
            throw new RuntimeException("Cannot find schedule with id - " + specificSchedule.getId());
        }

        Appointment specificAppointment = findAppointmentById(request.getAppointmentId());
        Schedule appointmentSchedule = specificAppointment.getSchedule();

        if (!specificSchedule.equals(appointmentSchedule)) {
            throw new RuntimeException("Cannot find appointment with id - " + specificAppointment.getId());
        }

        specificAppointment.setStatus(request.getStatus());
        specificAppointment.setPatient(specificAppointment.getPatient());
        appointmentRepository.save(specificAppointment);

        if (specificAppointment.getStatus() == 1) {
            return ConfirmDoctorResponse.builder()
                    .message("Accepted successfully.")
                    .description("See you that day!")
                    .build();
        } else if (specificAppointment.getStatus() == 2) {
            return ConfirmDoctorResponse.builder()
                    .message("Rejected successfully.")
                    .description("Sorry, Insufficient time available for a thorough examination.")
                    .build();
        }

        return ConfirmDoctorResponse.builder().message("Something went wrong").build();
    }

    public PatientScheduleResponse getPatientSchedule(int patientId) {
        try {
            Patient patient = patientService.findPatientById(patientId);
            List<Appointment> appointments = patient.getAppointments();

            if (appointments.isEmpty()) {
                return new PatientScheduleResponse("No appointments found for the patient.", null);
            } else {
                List<AppointmentDTO> appointmentDetail = appointments.stream()
                        .map(this::convertToAppointmentDTO)
                        .collect(Collectors.toList());
                return new PatientScheduleResponse("Patient appointment schedule retrieved successfully.", appointmentDetail);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching patient schedule.", e);
        }
    }
    private AppointmentDTO convertToAppointmentDTO(Appointment appointment) {
        return new AppointmentDTO(appointment.getTimeBooking(), appointment.getDateBooking(),appointment.getStatus());
    }

    public MakeAppointmentResponse makeAppointmentResponse (MakeAppointmentRequest request) {

        String invalidReason = getInvalidReason(request);

        if (invalidReason != null) {
            throw new InvalidAppointmentException(invalidReason);
        }

        Schedule newSchedule = new Schedule();
        DoctorUser doctorUser = userService.findDoctorUserByName(request.getDoctorName());
        List<Schedule> scheduleList = doctorUser.getScheduleList();

        // Get the list of sumBooking values from the existing schedules
        List<Integer> sumBookingList = scheduleList.stream()
                .map(Schedule::getSumBooking)
                .collect(Collectors.toList());

        // Find the maximum sumBooking value from the list
        int maxSumBooking = Collections.max(sumBookingList);


        newSchedule.setMaxBooking(10);
        newSchedule.setTime(request.getTime());
        newSchedule.setDoctorUser(doctorUser);
        newSchedule.setSumBooking(maxSumBooking + 1);

        LocalDate created = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = created.format(formatter);
        newSchedule.setDate(formattedDate);

        scheduleList.add(newSchedule); // add new schedule to doctor
        scheduleService.save(newSchedule); // save new schedule to database

        // update data in user table
        User theBookingUser = userService.getUserById(request.getBookingUserId());
        Role role = roleService.findRoleById(2);
        theBookingUser.setRole(role);
        userService.save(theBookingUser);

        // Check if a patient already exists for the booking user
        Patient existingPatient = patientService.findPatientByUser(theBookingUser);

        if (existingPatient == null) {
//            MedicalInformation newMedicalInformation = new MedicalInformation();
//            newMedicalInformation.setBasicDisease();
            // Create new patient and save
            Patient patient = new Patient();
            patient.setName(theBookingUser.getName());
            patient.setUser(theBookingUser);
            patient.setStatusId(0);
            patient.setDoctorUser(doctorUser);
//            patient.setMedicalInformation(1);
            patientService.save(patient);

            Appointment appointment = new Appointment();
            appointment.setPatient(patient);
            appointment.setTimeBooking(request.getTime());
            appointment.setDateBooking(formattedDate);
            appointment.setDoctorUser(doctorUser);
            appointment.setSchedule(newSchedule);
            appointment.setStatus(0);
            appointmentRepository.save(appointment);
        } else {
            // create new appointment for already patient
            Appointment appointment = new Appointment();
            appointment.setPatient(existingPatient);
            appointment.setTimeBooking(request.getTime());
            appointment.setDateBooking(formattedDate);
            appointment.setDoctorUser(doctorUser);
            appointment.setSchedule(newSchedule);
            appointment.setStatus(0);
            appointmentRepository.save(appointment);
        }

        return MakeAppointmentResponse
                .builder()
                .message("Make Appointment Successful")
                .build();
    }

    private String getInvalidReason(MakeAppointmentRequest request) {
        // get doctor name in request
        String name = request.getDoctorName();
        User user = userService.findByName(name);

        if (user == null) {
            return "Doctor with the given name does not exist.";
        }

        User bookingUser = userService.getUserById(request.getBookingUserId());
        if (bookingUser == null) {
            return "Booking user with the given ID does not exist.";
        }

        // Check the format of the time
        String timeFormatPattern = "HH:mm:ss"; // Change this to your desired format
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timeFormatPattern);

        try {
            LocalTime.parse(request.getTime(), timeFormatter);
        } catch (DateTimeParseException e) {
            return "Invalid time format. Please use the format " + timeFormatPattern;
        }

        int userId = user.getId();
        DoctorUser doctorUser = doctorUserService.findDoctorById(userId);
        List<Schedule> scheduleList = doctorUser.getScheduleList();

        for (Schedule schedule : scheduleList) {
            if (request.getTime().equals(schedule.getTime())) {
                return "The requested time slot is already booked.";
            }
            if (schedule.getSumBooking() > schedule.getMaxBooking()) {
                return "The requested time slot is fully booked.";
            }
        }

        return null; // Return null if the appointment request is valid
    }


    public void save(Appointment appointment) {
        appointmentRepository.save(appointment);
    }
}
