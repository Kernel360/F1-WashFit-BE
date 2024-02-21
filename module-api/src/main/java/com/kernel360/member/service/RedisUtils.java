package com.kernel360.member.service;

public interface RedisUtils {

    Object getData(String key);

    void setData(String key, String value);

    void setExpiringData(String key, String value, int duration);

    void expireData(String key);
}
