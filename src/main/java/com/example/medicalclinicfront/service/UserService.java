package com.example.medicalclinicfront.service;

import com.example.medicalclinicfront.domain.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private final String API_URL;
    private final RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.API_URL = "http://localhost:8080/med/users";
        this.restTemplate = restTemplate;
    }

    public List<UserDto> getAllUsers() {
        UserDto[] userDtos = restTemplate.getForObject(API_URL, UserDto[].class);
        return Arrays.asList(userDtos);
    }

    public UserDto getUserById(Long userId) {
        String url = API_URL + "/" + userId;
        return restTemplate.getForObject(url, UserDto.class, userId);
    }

    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        try {
            return restTemplate.postForEntity(
                    API_URL,
                    userDto,
                    UserDto.class
            );
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getRawStatusCode()).build();
        }
    }

    public UserDto updateUser(Long userId, UserDto userDto) {
        String url = API_URL + "/" + userId;
        restTemplate.put(url, userDto);
        return getUserById(userId);
    }

    public void deleteUser(Long userId) {
        String url = API_URL + "/" + userId;
        restTemplate.delete(url);
    }
}
