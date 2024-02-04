package com.kernel360.infra.reader;

import com.kernel360.entity.commoncode.CommonCode;
import com.kernel360.infra.mapper.CommonCodeMapper;
import com.kernel360.infra.repository.CommonCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommonCodeReaderImpl implements CommonCodeReader {

    private final CommonCodeRepository commonCodeRepository;
    private final CommonCodeMapper commonCodeMapper;
    public List<CommonCode> getCodes(String codeName) {

        return commonCodeRepository.findAllByUpperNameAndIsUsed(codeName,true);
    }

    public List<CommonCode> getCodesWithMapper(String codeName) {

        return commonCodeMapper.findAllByUpperNameAndIsUsed(codeName,true);
    }
}
