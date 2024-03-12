package com.kernel360.bbs.enumset;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BBSType {
    QNA, FREE, BOAST, RECOMMAND, NOTICE, REPLY;

    String type;
}
