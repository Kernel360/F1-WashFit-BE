package com.kernel360.member.enumset;

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

}
