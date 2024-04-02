package com.kernel360.bbs.repository;

import com.kernel360.bbs.dto.BBSListDto;
import com.kernel360.bbs.entity.BBS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface BBSRepository extends BBSRepositoryJPA, BBSRepositoryDSL {
    Page<BBSListDto> getBBSWithConditionByPage(String type, String keyword, Pageable pageable);
    Slice<BBSListDto> getBBSWithConditionBySlice(String type, String keyword, Pageable pageable);

    BBS findOneByBbsNo(Long bbsNo);

    Page<BBS> findAllByUpperNo(Long upperNo, Pageable pageable);

    void deleteByBbsNo(Long bbsNo);
}
