package com.kernel360.bbs.service;

import com.kernel360.bbs.dto.BBSDto;
import com.kernel360.bbs.dto.BBSListDto;
import com.kernel360.bbs.entity.BBS;
import com.kernel360.bbs.repository.BBSRepository;
import com.kernel360.member.dto.MemberDto;
import com.kernel360.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BBSService {
    private final BBSRepository bbsRepository;
    private final MemberService memberService;

    public Page<BBSListDto> getBBSWithCondition(String type, String keyword, Pageable pageable) {

        return bbsRepository.getBBSWithCondition(type, keyword, pageable);
    }

    public BBSDto getBBSView(Long bbsNo) {

        return BBSDto.from(bbsRepository.findOneByBbsNo(bbsNo));
    }

    public Page<BBSDto> getBBSReply(Long upperNo, Pageable pageable) {

        return bbsRepository.findAllByUpperNo(upperNo, pageable).map(BBSDto::from);
    }

    @Transactional
    public void saveBBS(BBSDto bbsDto, String id) {

        MemberDto memberDto = memberService.findByMemberId(id);

        bbsRepository.save(BBS.save(bbsDto.bbsNo(), bbsDto.upperNo(), bbsDto.type(), bbsDto.title(), bbsDto.contents(), true, 0L, memberDto.toEntity()));
    }

    @Transactional
    public void deleteBBS(Long bbsNo) {
        bbsRepository.deleteByBbsNo(bbsNo);
    }
}
