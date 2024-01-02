package com.kernel360.commoncode.service;

import com.kernel360.commoncode.dto.CommonCodeDto;
import com.kernel360.commoncode.repository.CommonCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonCodeService {

    @Autowired
    private CommonCodeRepository commonCodeRepository;

    public List<CommonCodeDto> getCodes(String codeName) {

        boolean isUsed = true;

        return commonCodeRepository.findAllByUpperNameAndIsUsed(codeName,isUsed)
                                   .stream()
                                   .map(CommonCodeDto::from)
                                   .toList();
    }
}
