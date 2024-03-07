package com.kernel360.member.service;

import com.kernel360.member.dto.MemberResponse;
import com.kernel360.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Page<MemberResponse> getAllMembers(Pageable pageable) {

        return memberRepository.findAllMember(pageable);
    }
}
