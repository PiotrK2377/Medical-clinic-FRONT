package com.example.medicalclinicfront.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String lastname;
    private String peselNumber;
    private String email;
    private String phoneNumber;
    private List<AppointmentDto> appointmentDtos;
    private List<ReviewDto> reviewDtos;

}
