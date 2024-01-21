package com.example.medicalclinicfront.service;


import com.example.medicalclinicfront.domain.DoctorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class DoctorService {

    private final String API_URL;
    private final RestTemplate restTemplate;

    public DoctorService(RestTemplate restTemplate) {
        this.API_URL = "http://localhost:8080/med/doctors";
        this.restTemplate = restTemplate;

    }

    public List<DoctorDto> getAllDoctors() {
            DoctorDto[] doctorDtos = restTemplate.getForObject(API_URL, DoctorDto[].class);
            return Arrays.asList(doctorDtos);
    }

    public DoctorDto getDoctorById(Long doctorId) {
        String url = API_URL + "/" + doctorId;
        return restTemplate.getForObject(url, DoctorDto.class);
    }

    public ResponseEntity<DoctorDto> createDoctor(DoctorDto doctorDto) {
        try {
            return restTemplate.postForEntity(
                    API_URL,
                    doctorDto,
                    DoctorDto.class
            );
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getRawStatusCode()).build();
        }
    }

    public DoctorDto updateDoctor(Long doctorId, DoctorDto doctorDto) {
        String url = API_URL + "/" + doctorId;
        restTemplate.put(url, doctorDto);
        return getDoctorById(doctorId);
    }

    public void deleteDoctor(Long doctorId) {
        String url = API_URL + "/" + doctorId;
        restTemplate.delete(url);
    }
}
