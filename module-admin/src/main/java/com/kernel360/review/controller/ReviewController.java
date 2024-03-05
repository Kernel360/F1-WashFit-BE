package com.kernel360.review.controller;

import com.kernel360.response.ApiResponse;
import com.kernel360.review.code.ReviewBusinessCode;
import com.kernel360.review.dto.AdminReviewDto;
import com.kernel360.review.service.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
// FIXME :: 기능에 멤버 정보가 담겨있지 않으므로 논의하여 추가하여야 함!! 누가 작성하였는지에 대한 정보는 꼭 필요하다!!
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/reviews")
public class ReviewController {

    private final ReviewServiceImpl reviewService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdminReviewDto>>> getAllReviews(
            @RequestParam(name = "sortBy", defaultValue = "reviewNo", required = false) String sortBy,
            Pageable pageable) {

        return ApiResponse.toResponseEntity(ReviewBusinessCode.SUCCESS_GET_REVIEWS,
                reviewService.getReviews(sortBy, pageable));
    }

    @GetMapping("/product")
    public ResponseEntity<ApiResponse<Page<AdminReviewDto>>> getReviewsByProduct(
            @RequestParam(name = "productNo") Long productNo,
            @RequestParam(name = "sortBy", defaultValue = "reviewNo", required = false) String sortBy,
            Pageable pageable) {

        return ApiResponse.toResponseEntity(ReviewBusinessCode.SUCCESS_GET_REVIEWS,
                reviewService.getReviewsByProduct(productNo, sortBy, pageable));
    }

    @GetMapping("/member")
    public ResponseEntity<ApiResponse<Page<AdminReviewDto>>> getReviewsByMember(
            @RequestParam(name = "memberNo") Long memberNo,
            @RequestParam(name = "sortBy", defaultValue = "reviewNo", required = false) String sortBy,
            Pageable pageable) {

        return ApiResponse.toResponseEntity(ReviewBusinessCode.SUCCESS_GET_REVIEWS,
                reviewService.getReviewsByMember(memberNo, sortBy, pageable));
    }

    @GetMapping("/{reviewNo}")
    public ResponseEntity<ApiResponse<AdminReviewDto>> getReview(@PathVariable Long reviewNo) {

        return ApiResponse.toResponseEntity(ReviewBusinessCode.SUCCESS_GET_REVIEW, reviewService.getReview(reviewNo));
    }

    @DeleteMapping("/{reviewNo}")
    public <T> ResponseEntity<ApiResponse<T>> deleteReview(@PathVariable Long reviewNo) {
        reviewService.deleteReview(reviewNo);

        return ApiResponse.toResponseEntity(ReviewBusinessCode.SUCCESS_DELETE_REVIEW);
    }
}
