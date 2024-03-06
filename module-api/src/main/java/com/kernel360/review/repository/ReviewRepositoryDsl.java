package com.kernel360.review.repository;

import com.kernel360.review.dto.ReviewSearchDto;
import com.kernel360.review.dto.ReviewSearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryDsl {
    Page<ReviewSearchResult> findAllByCondition(ReviewSearchDto condition, Pageable pageable);

    ReviewSearchResult findByReviewNo(Long reviewNo);
}
