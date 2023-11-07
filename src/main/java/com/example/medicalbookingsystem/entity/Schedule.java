package com.example.medicalbookingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name="schedule")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String date;
    private String time;
    private int maxBooking;
    private int sumBooking;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="doctor_id")
    private DoctorUser doctorUser;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY, cascade =
            {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.PERSIST})
    private List<Appointment> appointmentList;

}
