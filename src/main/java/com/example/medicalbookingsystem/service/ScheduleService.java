package com.example.medicalbookingsystem.service;
import com.example.medicalbookingsystem.dao.ScheduleRepository;
import com.example.medicalbookingsystem.entity.*;
import com.example.medicalbookingsystem.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserService userService;
    private final DoctorUserService doctorUserService;
    private final PatientService patientService;
    private final RoleService roleService;
//    private final AppointmentServiceImpl appointmentService;
    public List<Schedule> findTop3SumBooking() {
        return scheduleRepository.findTop3ByOrderBySumBookingDesc();
    }


//    private boolean isRequestValid(MakeAppointmentRequest request) {
//        // get doctor name in request
//        String name = request.getDoctorName();
//        User user = userService.findByName(name);
//
//        if(user == null) {
//            return false;
//        }
//
//        User bookingUser = userService.getUserById(request.getBookingUserId());
//        if(bookingUser == null) {
//            return false;
//        }
//
//        // Check the format of the date
//        String dateFormatPattern = "yyyy-MM-dd"; // Change this to your desired format
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormatPattern);
//
//        try {
//            LocalDate.parse(request.getTime(), dateFormatter);
//        } catch (DateTimeParseException e) {
//            return false; // Return false if the date does not match the specified format
//        }
//
//        int userId = user.getId();
//        DoctorUser doctorUser = doctorUserService.findDoctorById(userId);
//        List<Schedule> scheduleList = doctorUser.getScheduleList();
//
//        for(Schedule schedule : scheduleList) {
//            if(request.getTime().equals(schedule.getTime())
//                    || schedule.getSumBooking() > schedule.getMaxBooking()) {
//                return false;
//            }
//        }
//        return true;
//    }

    public Schedule findScheduleById(int theId) {
        Optional<Schedule> result = scheduleRepository.findById(theId);
        Schedule theSchedule = null;

        if(result.isPresent()) {
            theSchedule = result.get();
        } else {
            throw new UserNotFoundException("Did not find the schedule have id - " + theId);
        }
        return theSchedule;
    }

    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }
}
