package com.kernel360.commoncode.service;

import com.kernel360.commoncode.dto.CommonCodeDto;
import com.kernel360.commoncode.mapper.CommonCodeMapper;
import com.kernel360.commoncode.repository.CommonCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonCodeService {

    private final CommonCodeRepository commonCodeRepository;
    private final CommonCodeMapper commonCodeMapper;

    public List<CommonCodeDto> getCodes(String codeName) {

        //
        return commonCodeRepository.findAllByUpperNameAndIsUsed(codeName,true)
                                   .stream()
                                   .map(CommonCodeDto::from)
                                   .toList();
    }

    public List<CommonCodeDto> getCodesMapper(String codeName) {

        return commonCodeMapper.findAllByUpperNameAndIsUsed(codeName,true)
                                   .stream()
                                   .map(CommonCodeDto::from)
                                   .toList();
    }
}
