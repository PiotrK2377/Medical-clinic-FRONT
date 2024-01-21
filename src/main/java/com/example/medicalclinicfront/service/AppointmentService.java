package com.example.medicalclinicfront.service;

import com.example.medicalclinicfront.domain.AppointmentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class AppointmentService {

    private final String API_URL;
    private final RestTemplate restTemplate;

    public AppointmentService(RestTemplate restTemplate) {
        this.API_URL = "http://localhost:8080/med/appointments";
        this.restTemplate = restTemplate;
    }

    public List<AppointmentDto> getAllAppointments() {
        AppointmentDto[] appointmentDtos = restTemplate.getForObject(API_URL, AppointmentDto[].class);
        return Arrays.asList(appointmentDtos);
    }

    public AppointmentDto getAppointmentById(Long appointmentId) {
        String url = API_URL + "/" + appointmentId;
        return restTemplate.getForObject(url, AppointmentDto.class);
    }

    public ResponseEntity<AppointmentDto> createAppointment(AppointmentDto appointmentDto) {
        try {
            return restTemplate.postForEntity(
                    API_URL,
                    appointmentDto,
                    AppointmentDto.class
            );
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getRawStatusCode()).build();
        }
    }

    public AppointmentDto updateAppointment(Long appointmentId, AppointmentDto appointmentDto) {
        String url = API_URL + "/" + appointmentId;
        restTemplate.put(url, appointmentId);
        return getAppointmentById(appointmentId);
    }

    public void deleteAppointment(Long appointmentId) {
        String url = API_URL + "/" + appointmentId;
        restTemplate.delete(url);
    }
}
