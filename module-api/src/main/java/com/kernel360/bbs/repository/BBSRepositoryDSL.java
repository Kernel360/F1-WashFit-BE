package com.kernel360.bbs.repository;

import com.kernel360.bbs.dto.BBSListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BBSRepositoryDSL {
    Page<BBSListDto> getBBSWithCondition(String bbsType, String keyword, Pageable pageable);
}
