package com.kernel360.global.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@Component
public class LogFilter implements Filter {
    private static final Trie<String, Integer> DENIED_LIST = new PatriciaTrie<>();

    static {
        DENIED_LIST.put("/actuator/prometheus", 0);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper request = new ContentCachingRequestWrapper((HttpServletRequest) req);
        ContentCachingResponseWrapper response = new ContentCachingResponseWrapper((HttpServletResponse) res);

        printLog(chain, request, response);
    }

    private static void printLog(FilterChain chain, ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) throws IOException, ServletException {
        log.info("##### INIT URI: {}", request.getRequestURI());

        chain.doFilter(request, response);

        // Request
        StringBuilder requestHeaderValues = new StringBuilder();
        request.getHeaderNames().asIterator().forEachRemaining(headerKey -> {
            String headerValue = request.getHeader(headerKey);

            requestHeaderValues
                    .append("[")
                    .append(headerKey)
                    .append(" : ")
                    .append(headerValue)
                    .append("] ");
        });

        String requestBody = new String(request.getContentAsByteArray());
        String uri = request.getRequestURI();
        String method = request.getMethod();
        log.info("##### REQUEST ##### uri: {}, method: {}, header: {}, body: {}", uri, method, requestHeaderValues, requestBody);

        // Response
        StringBuilder responseHeaderValues = new StringBuilder();
        response.getHeaderNames().forEach(headerKey -> {
            String headerValue = response.getHeader(headerKey);

            responseHeaderValues
                    .append("[")
                    .append(headerKey)
                    .append(" : ")
                    .append(headerValue)
                    .append("] ");
        });

        String responseBody = new String(response.getContentAsByteArray());
        if(!DENIED_LIST.containsKey(uri) && !uri.startsWith("/docs/")) {
            log.info("##### RESPONSE ##### uri: {}, method: {}, header: {}, body: {}", uri, method, responseHeaderValues, responseBody);
        }
        response.copyBodyToResponse();
    }
}
