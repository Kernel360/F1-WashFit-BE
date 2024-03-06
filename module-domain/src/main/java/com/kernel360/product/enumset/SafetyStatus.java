package com.kernel360.product.enumset;

import lombok.Getter;

@Getter
public enum SafetyStatus {
    SAFE(1),
    CONCERN(2),
    DANGER(3);
    private final Integer code;

    SafetyStatus(Integer code) {
        this.code = code;
    }

}
