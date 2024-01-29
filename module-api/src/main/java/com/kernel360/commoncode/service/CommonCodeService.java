package com.kernel360.commoncode.service;

import com.kernel360.commoncode.dto.CommonCodeDto;
import com.kernel360.commoncode.repository.CommonCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonCodeService {

    final CommonCodeRepository commonCodeRepository;

    @Autowired
    public CommonCodeService(CommonCodeRepository commonCodeRepository) {
        this.commonCodeRepository = commonCodeRepository;
    }

    public List<CommonCodeDto> getCodes(String codeName) {

        return commonCodeRepository.findAllByUpperNameAndIsUsed(codeName,true)
                                   .stream()
                                   .map(CommonCodeDto::from)
                                   .toList();
    }
}
