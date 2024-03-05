package com.kernel360.review.service;

import com.kernel360.review.dto.AdminReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    Page<AdminReviewDto> getReviewsByProduct(Long productNo, String sortBy, Pageable pageable);
    Page<AdminReviewDto> getReviewsByMember(Long memberNo,String sortBy,Pageable pageable);
    Page<AdminReviewDto> getReviews(String sortBy, Pageable pageable);

    AdminReviewDto getReview(Long reviewNo);

    void deleteReview(Long reviewNo);
}
