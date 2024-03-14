package com.kernel360.member.enumset;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Gender {
    MALE(0),
    FEMALE(1),
    OTHERS(99);

    private final int value;

}
