package com.kernel360.review.repository;

import com.kernel360.review.dto.ReviewResponse;
import com.kernel360.review.dto.ReviewSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryDsl {
    Page<ReviewResponse> findAllByCondition(ReviewSearchDto condition, Pageable pageable);

    ReviewResponse findByReviewNo(Long reviewNo);
}
