package com.example.medicalclinicfront.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {

    private Long id;
    private String name;
    private String lastname;
    private DoctorSpecialization specialization;
    private String numberPWZ;
    private List<AppointmentDto> appointmentDtos;
    private List<ReviewDto> reviewDtos;

    public DoctorDto(String name, String lastname, DoctorSpecialization specialization, String numberPWZ) {
        this.name = name;
        this.lastname = lastname;
        this.specialization = specialization;
        this.numberPWZ = numberPWZ;
    }
}