package com.kernel360.member.enumset;

import com.kernel360.exception.BusinessException;
import com.kernel360.member.code.MemberErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Age {
    AGE_20(20),
    AGE_30(30),
    AGE_40(40),
    AGE_50(50),
    AGE_60(60),
    AGE_99(99);

    private final int value;

    public static String ordinalToValue(int key) {
        for (Age age : values()) {
            if (age.ordinal() == key) {
                return age.name();
            }
        }
        throw new BusinessException(MemberErrorCode.FAILED_NOT_MAPPING_ORDINAL_TO_VALUE);
    }
}
