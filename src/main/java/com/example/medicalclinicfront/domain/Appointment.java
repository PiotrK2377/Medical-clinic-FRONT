package com.example.medicalclinicfront.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "APPOINTMENTS")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "DOCTOR_ID")
    private Doctor doctor;

    @Column(name = "APPOINTMENT_DATE")
    private LocalDate appointmentDate;

    @Column(name = "STATUS")
    private String status;
}
