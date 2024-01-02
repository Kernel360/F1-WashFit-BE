package com.kernel360.modulebatch.controller;

import org.springframework.http.ResponseEntity;
public interface ProductApiClient {

    ResponseEntity<?> getRawData(int page);

    String buildUrl(int pageNumber);
}
