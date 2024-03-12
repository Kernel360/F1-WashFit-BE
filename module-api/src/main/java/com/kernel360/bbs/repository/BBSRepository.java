package com.kernel360.bbs.repository;

import com.kernel360.bbs.dto.BBSListDto;
import com.kernel360.bbs.entity.BBS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BBSRepository extends BBSRepositoryJPA, BBSRepositoryDSL {
    Page<BBSListDto> getBBSWithCondition(String type, String keyword, Pageable pageable);

    BBS findOneByBbsNo(Long bbsNo);

    Page<BBS> findAllByUpperNo(Long upperNo, Pageable pageable);

    void deleteByBbsNo(Long bbsNo);
}
