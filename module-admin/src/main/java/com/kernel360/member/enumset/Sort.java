package com.kernel360.member.enumset;

public enum Sort {
    ID_ORDER("id-order"),
    GENDER_ORDER("gender-order"),
    AGE_ORDER("age-order"),
    REGISTER_ORDER("register-order"),
    RECENT_PRODUCT_ORDER("recent-order");


    private final String orderType;

    Sort(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        return orderType;
    }
}



