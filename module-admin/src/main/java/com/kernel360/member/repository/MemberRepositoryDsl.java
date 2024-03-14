package com.kernel360.member.repository;

import com.kernel360.member.dto.MemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryDsl {
    Page<MemberResponse> findAllMember(Pageable pageable);

}
