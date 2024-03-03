package com.kernel360.review.repository;

import com.kernel360.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepositoryDomain extends JpaRepository<Review, Long> {
    Review findByReviewNo(Long reviewNo);
}
