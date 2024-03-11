package com.kernel360.washzonereview;

import com.kernel360.washzonereview.entity.WashzoneReview;
import com.kernel360.washzonereview.repository.WashzoneReviewRepositoryJpa;

import java.util.Optional;

public interface WashzoneReviewRepository extends WashzoneReviewRepositoryJpa, WashzoneReviewRepositoryDsl {
    Optional<WashzoneReview> findByWashzoneReviewNoAndIsVisibleTrue(Long washzoneReviewNo);
}
