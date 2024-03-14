package com.kernel360.review.repository;

import com.kernel360.review.dto.ReviewSearchDto;
import com.kernel360.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryDsl {
    Page<Review> findAllByCondition(ReviewSearchDto condition, Pageable pageable);

    Page<Review> findAll(String sortBy, Pageable pageable);
}
