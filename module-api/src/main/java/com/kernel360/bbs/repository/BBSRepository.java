package com.kernel360.bbs.repository;

import com.kernel360.bbs.dto.BBSDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface BBSRepository {
    Page<BBSDto> getBBSWithCondition(String sortType, String keyword, Pageable pageable);
}
