package com.kernel360.review.controller;

import com.kernel360.response.ApiResponse;
import com.kernel360.review.code.ReviewBusinessCode;
import com.kernel360.review.dto.ReviewResponseDto;
import com.kernel360.review.dto.ReviewRequestDto;
import com.kernel360.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<ReviewResponseDto>>> getReviewsByProduct(
            @RequestParam(name = "productNo") Long productNo,
            @RequestParam(name = "sortBy", defaultValue = "reviewNo", required = false) String sortBy,
            Pageable pageable) {

        return ApiResponse.toResponseEntity(ReviewBusinessCode.SUCCESS_GET_REVIEWS, reviewService.getReviewsByProduct(productNo, sortBy, pageable));
    }

    @GetMapping("/{reviewNo}")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> getReview(@PathVariable Long reviewNo) {

        return ApiResponse.toResponseEntity(ReviewBusinessCode.SUCCESS_GET_REVIEW, reviewService.getReview(reviewNo));
    }

    @PostMapping("")
    public <T> ResponseEntity<ApiResponse<T>> createReview(
            @RequestPart ReviewRequestDto reviewRequestDto,
            @RequestPart(required = false) List<MultipartFile> files) {
        reviewService.createReview(reviewRequestDto, files);

        return ApiResponse.toResponseEntity(ReviewBusinessCode.SUCCESS_CREATE_REVIEW);
    }

    @PatchMapping("")
    public <T> ResponseEntity<ApiResponse<T>> updateReview(
            @RequestPart ReviewRequestDto reviewRequestDto,
            @RequestPart(required = false) List<MultipartFile> files) {
        reviewService.updateReview(reviewRequestDto, files);

        return ApiResponse.toResponseEntity(ReviewBusinessCode.SUCCESS_UPDATE_REVIEW);
    }

    @DeleteMapping("/{reviewNo}")
    public <T> ResponseEntity<ApiResponse<T>> deleteReview(@PathVariable Long reviewNo) {
        reviewService.deleteReview(reviewNo);

        return ApiResponse.toResponseEntity(ReviewBusinessCode.SUCCESS_DELETE_REVIEW);
    }
}
