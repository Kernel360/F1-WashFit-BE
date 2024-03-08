package com.kernel360.bbs.repository;

import com.kernel360.bbs.entity.BBS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BBSRepositoryDSL {
    Page<BBS> getBBSWithCondition(String bbsType, String keyword, Pageable pageable);
}
