package com.example.medicalclinicfront.service;

import com.example.medicalclinicfront.domain.ReviewDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ReviewService {

    private final String API_URL;
    private final RestTemplate restTemplate;

    public ReviewService(RestTemplate restTemplate) {
        this.API_URL = "http://localhost:8080/med/reviews";
        this.restTemplate = restTemplate;
    }

    public List<ReviewDto> getAllReviews() {
        ReviewDto[] reviewDtos = restTemplate.getForObject(API_URL, ReviewDto[].class);
        return Arrays.asList(reviewDtos);
    }

    public ReviewDto getReviewById(Long reviewId) {
        String url = API_URL + "/" + reviewId;
        return restTemplate.getForObject(url, ReviewDto.class);
    }

    public ResponseEntity<ReviewDto> createReview(ReviewDto reviewDto) {
        try {
            return restTemplate.postForEntity(
                    API_URL,
                    reviewDto,
                    ReviewDto.class
            );
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getRawStatusCode()).build();
        }
    }

    public ReviewDto updateReview(Long reviewId, ReviewDto reviewDto) {
        String url = API_URL + "/" + reviewId;
        restTemplate.put(url, reviewDto);
        return getReviewById(reviewId);
    }

    public void deleteReview(Long reviewId) {
        String url = API_URL + "/" + reviewId;
        restTemplate.delete(url);
    }
}
