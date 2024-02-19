package com.kernel360.review.controller;

import com.kernel360.response.ApiResponse;
import com.kernel360.review.code.ReviewBusinessCode;
import com.kernel360.review.dto.ReviewDto;
import com.kernel360.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/product/{productNo}")
    public ResponseEntity<ApiResponse<List<ReviewDto>>> getReviewsByProduct(@PathVariable Long productNo) {
        List<ReviewDto> reviews = reviewService.getReviewsByProduct(productNo);

        return ApiResponse.toResponseEntity(ReviewBusinessCode.SUCCESS_GET_REVIEWS, reviews);
    }

    @GetMapping("/{reviewNo}")
    public ResponseEntity<ApiResponse<ReviewDto>> getReview(@PathVariable Long reviewNo) {
        ReviewDto review = reviewService.getReview(reviewNo);

        return ApiResponse.toResponseEntity(ReviewBusinessCode.SUCCESS_GET_REVIEW, review);
    }

    @PostMapping("")
    public <T> ResponseEntity<ApiResponse<T>> createReview(@RequestBody ReviewDto reviewDto) {
        reviewService.createReview(reviewDto);

        return ApiResponse.toResponseEntity(ReviewBusinessCode.SUCCESS_CREATE_REVIEW);
    }

    @PatchMapping("")
    public <T> ResponseEntity<ApiResponse<T>> updateReview(@RequestBody ReviewDto reviewDto) {
        reviewService.updateReview(reviewDto);

        return ApiResponse.toResponseEntity(ReviewBusinessCode.SUCCESS_UPDATE_REVIEW);
    }

    @DeleteMapping("/{reviewNo}")
    public <T> ResponseEntity<ApiResponse<T>> deleteReview(@PathVariable Long reviewNo) {
        reviewService.deleteReview(reviewNo);

        return ApiResponse.toResponseEntity(ReviewBusinessCode.SUCCESS_DELETE_REVIEW);
    }
}
