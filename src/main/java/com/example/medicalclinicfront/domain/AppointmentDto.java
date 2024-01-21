package com.example.medicalclinicfront.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {

    private Long id;
    private UserDto userDto;
    private DoctorDto doctorDto;
    private LocalDate appointmentDate;
    private AppointmentStatus status;
}
