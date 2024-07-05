package com.spring.bookstore.controller;

import com.spring.bookstore.dto.ReviewRequestDto;
import com.spring.bookstore.dto.ReviewDto;
import com.spring.bookstore.service.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/review")
@AllArgsConstructor
public class ReviewController {

    private ReviewService reviewService;
    private ModelMapper modelMapper;

    @GetMapping("{id}")
    public ResponseEntity<?> getReviewById(@PathVariable("id") int reviewId) {
        try {
            ReviewDto reviewDto = this.modelMapper.map(this.reviewService.getReviewById(reviewId), ReviewDto.class);
            return ResponseEntity.ok(reviewDto);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAllReview() {
        List<ReviewDto> reviewDtoList = this.reviewService.getAllReviews()
                .stream()
                .map(review -> this.modelMapper.map(review, ReviewDto.class))
                .toList();
        return ResponseEntity.ok(reviewDtoList);
    }

    @PostMapping
    public ResponseEntity<ReviewDto> addReview(@RequestBody ReviewRequestDto review) {
        ReviewDto reviewDto = this.modelMapper.map(this.reviewService.addReview(review), ReviewDto.class);
        return new ResponseEntity<>(reviewDto, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateReview(@PathVariable("id") int reviewId,
                                          @RequestBody ReviewRequestDto review) {
        try {
            ReviewDto reviewDto = this.modelMapper.map(this.reviewService.updateReview(reviewId, review), ReviewDto.class);
            return ResponseEntity.ok(reviewDto);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteReview(@PathVariable("id") int reviewId) {
        try {
            this.reviewService.deleteReview(reviewId);
            return ResponseEntity.ok("The review has been deleted successfully!");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
