package com.kernel360.modulebatch.reportedproduct.client;

public interface ApiClient<T> {

    String getXmlResponse(T parameter);

    String buildUri(T parameter);
}
