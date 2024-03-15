package com.kernel360.washzonereview.repository;

import com.kernel360.washzonereview.entity.WashzoneReview;

import java.util.Optional;

public interface WashzoneReviewRepository extends WashzoneReviewRepositoryJpa, WashzoneReviewRepositoryDsl {
    Optional<WashzoneReview> findByWashzoneReviewNoAndIsVisibleTrue(Long washzoneReviewNo);
}
