package com.kernel360.modulebatch.controller;

import static java.lang.System.getenv;

import com.kernel360.product.entity.ReportedProduct;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class ReportedProductApiClient implements ProductApiClient {
    private final RestClient restClient;
    private static final String AUTH_KEY = getenv().get("API_AUTH_KEY");

    public ReportedProductApiClient() {
        this.restClient = RestClient.builder()
                                    .messageConverters(converters -> converters.add(
                                            new MappingJackson2XmlHttpMessageConverter()))
                                    .build();
    }

    @Override
    public ResponseEntity<List<ReportedProduct>> getRawData(int pageNumber) {

        return restClient.get().uri(buildUrl(pageNumber))
                         .accept(MediaType.APPLICATION_XML)
                         .acceptCharset(StandardCharsets.UTF_8)
                         .retrieve()
                         .onStatus(HttpStatusCode::is4xxClientError,
                                 ((request, response) -> {
                                     log.debug("[ERROR] :: 4XX 에러 발생"
                                             + response.getStatusText());
                                     throw new RuntimeException(
                                             response.getStatusCode()
                                                     + response.getHeaders()
                                                               .toString());
                                 }))
                         .onStatus(HttpStatusCode::is5xxServerError,
                                 ((request, response) -> {
                                     log.debug("[ERROR] :: 5XX 에러 발생"
                                             + response.getStatusText());
                                     throw new RuntimeException(
                                             response.getStatusCode()
                                                     + response.getHeaders()
                                                               .toString());
                                 }))
                         .onStatus(HttpStatusCode::is2xxSuccessful,
                                 ((request, response) -> {
                                     log.info("[INFO] :: api 요청 성공"
                                             + response.getBody());
                                 }))
                         .toEntity(new ParameterizedTypeReference<>() {
                         });
    }

    @Override
    public String buildUrl(int pageNumber) {

        return UriComponentsBuilder.fromHttpUrl("https://ecolife.me.go.kr/openapi/ServiceSvl")
                                   .queryParam("AuthKey", AUTH_KEY)
                                   .queryParam("ServiceName", "slfsfcfst02List")
                                   .queryParam("PageCount", "20")
                                   .queryParam("PageNum", String.valueOf(pageNumber))
                                   .toUriString();
    }
}
