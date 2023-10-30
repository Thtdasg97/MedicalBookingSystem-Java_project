package com.example.asm03spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="doctor_user")
public class DoctorUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="doctor_id")
    private User user;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="specialization_id")
    private Specialization specialization;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="clinic_id")
    private Clinic clinic;

    @OneToMany(fetch = FetchType.LAZY, cascade =
            {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.PERSIST},
            mappedBy = "doctorUser")
    private List<Schedule> scheduleList;

    @OneToMany(mappedBy = "doctorUser", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Patient> patientList;

    @OneToMany(fetch = FetchType.LAZY, cascade =
            {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.PERSIST},
            mappedBy = "doctorUser")
    private List<Appointment> appointments;

    @OneToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="doctor_info_id")
    private DoctorInfo doctorInfo;

    private Integer statusId;

    public void add(Schedule schedule) {
        if(scheduleList == null) {
            scheduleList = new ArrayList<>();
        }
        scheduleList.add(schedule);
    }
    public void add(Patient patient) {
        if(patientList == null) {
            patientList = new ArrayList<>();
        }
        patientList.add(patient);
    }

}
