package com.kernel360.bbs.repository;

import com.kernel360.bbs.dto.BBSListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface BBSRepositoryDSL {
    Page<BBSListDto> getBBSWithConditionByPage(String bbsType, String keyword, Pageable pageable);

    Slice<BBSListDto> getBBSWithConditionBySlice(String bbsType, String keyword, Pageable pageable);
}
