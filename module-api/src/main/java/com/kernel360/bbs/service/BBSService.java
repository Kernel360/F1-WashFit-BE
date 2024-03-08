package com.kernel360.bbs.service;

import com.kernel360.bbs.dto.BBSDto;
import com.kernel360.bbs.repository.BBSRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BBSService {
    private final BBSRepository bbsRepository;

    public Page<BBSDto> getBBSWithCondition(String bbsType, String keyword, Pageable pageable) {

        return bbsRepository.getBBSWithCondition(bbsType, keyword, pageable).map(BBSDto::from);
    }
}
