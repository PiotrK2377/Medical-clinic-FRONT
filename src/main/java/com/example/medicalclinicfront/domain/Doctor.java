package com.example.medicalclinicfront.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "DOCTORS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "SPECIALIZATION")
    private DoctorSpecialization specialization;

    @Column(name = "PWZ_NUMBER")
    private String numberPWZ;

    @OneToMany(
            targetEntity = Appointment.class,
            mappedBy = "doctor",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Appointment> appointments;

    @OneToMany(
            targetEntity = Review.class,
            mappedBy = "doctor",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Review> reviews;

    public Doctor(Long id, String name, String lastname, DoctorSpecialization specialization, String numberPWZ) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.specialization = specialization;
        this.numberPWZ = numberPWZ;
    }

}