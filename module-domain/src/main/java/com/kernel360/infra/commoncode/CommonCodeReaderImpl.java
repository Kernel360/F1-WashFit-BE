package com.kernel360.infra.commoncode;

import com.kernel360.commoncode.service.CommonCodeReader;
import com.kernel360.entity.commoncode.CommonCode;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CommonCodeReaderImpl implements CommonCodeReader {

    private final CommonCodeRepository commonCodeRepository;
    public List<CommonCode> getCodes(String codeName) {

        return commonCodeRepository.findAllByUpperNameAndIsUsed(codeName,true);
    }
}
