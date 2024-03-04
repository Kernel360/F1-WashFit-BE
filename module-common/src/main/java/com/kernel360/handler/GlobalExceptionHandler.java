package com.kernel360.handler;

import com.kernel360.code.ErrorCode;
import com.kernel360.code.common.CommonErrorCode;
import com.kernel360.code.jwt.JwtErrorCode;
import com.kernel360.exception.BusinessException;
import com.kernel360.response.ErrorResponse;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
        log.error("handleBusinessException", e);

        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response = ErrorResponse.of(errorCode);

        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("handleException", e);

        final ErrorResponse response = ErrorResponse.of(CommonErrorCode.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MalformedJwtException.class)
    protected ResponseEntity<ErrorResponse> handleMalformedJwtException(final MalformedJwtException e) {
        log.error("handleMalformedJwtException", e);

        final ErrorResponse response = ErrorResponse.of(JwtErrorCode.FAILED_MALFORMED_JWT);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SignatureException.class)
    protected ResponseEntity<ErrorResponse> handleJwtSignatureException(final SignatureException e) {
        log.error("handleJwtSignatureException", e);

        final ErrorResponse response = ErrorResponse.of(JwtErrorCode.FAILED_SIGNATURE_JWT);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ErrorResponse> handleNullPointerException(final NullPointerException e) {
        log.error("handleNullPointerException", e);

        final ErrorResponse response = ErrorResponse.of(CommonErrorCode.NOT_FOUND_RESOURCE);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ResponseEntity<ErrorResponse> handleMissingHeaderException(final MissingRequestHeaderException e) {
        log.error("handleMissingHeaderException", e);

        final ErrorResponse response = ErrorResponse.of(CommonErrorCode.INVALID_REQUEST_HEADERS);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(final IllegalArgumentException e){
        log.error("handleIllegalArgumentException",e);

        final ErrorResponse response = ErrorResponse.of(CommonErrorCode.INVALID_ARGUMENT);

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e){
        log.error("handleHttpRequestMethodNotSupportedException",e);

        final ErrorResponse response =ErrorResponse.of(CommonErrorCode.INVALID_HTTP_REQUEST_METHOD);

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ErrorResponse> handleMissingParameterException(final MissingServletRequestParameterException e) {
        log.error("handleMissingParameterException", e);

        final ErrorResponse response = ErrorResponse.of(CommonErrorCode.INVALID_REQUEST_PARAMETER);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
