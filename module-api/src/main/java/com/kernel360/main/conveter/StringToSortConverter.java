package com.kernel360.main.conveter;

import com.kernel360.exception.BusinessException;
import com.kernel360.main.code.ConverterErrorCode;
import com.kernel360.main.controller.Sort;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSortConverter implements Converter<String, Sort> {

    @Override
    public Sort convert(String source) {
        for (Sort sort : Sort.values()) {
            if (sort.getOrderType().equalsIgnoreCase(source)) {
                return sort;
            }
        }
        throw new BusinessException(ConverterErrorCode.NOT_FOUND_CONVERTER);
    }
}
