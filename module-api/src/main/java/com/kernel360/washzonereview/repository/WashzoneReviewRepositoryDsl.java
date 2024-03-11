package com.kernel360.washzonereview.repository;

import com.kernel360.washzonereview.dto.WashzoneReviewSearchDto;
import com.kernel360.washzonereview.dto.WashzoneReviewSearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WashzoneReviewRepositoryDsl {
    Page<WashzoneReviewSearchResult> findAllByCondition(WashzoneReviewSearchDto condition, Pageable pageable);

    WashzoneReviewSearchResult findByWashzoneReviewNo(Long washzoneReviewNo);
}
