package com.kernel360.review.repository;

import com.kernel360.review.entity.Review;

import java.util.Optional;

public interface ReviewRepository extends ReviewRepositoryJpa, ReviewRepositoryDsl {
    Optional<Review> findByIdAndIsVisibleTrue(Long reviewNo);
}
