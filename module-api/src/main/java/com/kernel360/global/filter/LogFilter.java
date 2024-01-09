package com.kernel360.global.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@Component
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper res = new ContentCachingResponseWrapper((HttpServletResponse) response);
        log.info("##### INIT URI: {}", req.getRequestURI());

        chain.doFilter(req, res);

        // Request
        StringBuilder reqHeaderValues = new StringBuilder();
        req.getHeaderNames().asIterator().forEachRemaining(headerKey -> {
            String headerValue = req.getHeader(headerKey);

            reqHeaderValues
                    .append("[")
                    .append(headerKey)
                    .append(" : ")
                    .append(headerValue)
                    .append("] ");
        });

        String requestBody = new String(req.getContentAsByteArray());
        String uri = req.getRequestURI();
        String method = req.getMethod();
        log.info("##### REQUEST ##### uri: {}, method: {}, header: {}, body: {}", uri, method, reqHeaderValues, requestBody);

        // Response
        StringBuilder resHeaderValues = new StringBuilder();
        res.getHeaderNames().forEach(headerKey -> {
            String headerValue = res.getHeader(headerKey);

            resHeaderValues
                    .append("[")
                    .append(headerKey)
                    .append(" : ")
                    .append(headerValue)
                    .append("] ");
        });

        String responseBody = new String(res.getContentAsByteArray());
        log.info("##### RESPONSE ##### uri: {}, method: {}, header: {}, body: {}", uri, method, resHeaderValues, responseBody);

        res.copyBodyToResponse();
    }
}
