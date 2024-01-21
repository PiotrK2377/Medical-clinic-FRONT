package com.example.medicalclinicfront.domain;


import javax.persistence.*;
import java.util.List;

@Entity(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "PESEL_NUMBER")
    private String peselNumber;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @OneToMany(
            targetEntity = Appointment.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Appointment> appointments;

    @OneToMany(
            targetEntity = Review.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Review> reviews;
}
