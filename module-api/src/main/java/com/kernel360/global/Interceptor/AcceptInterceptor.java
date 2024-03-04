package com.kernel360.global.Interceptor;

import com.kernel360.auth.service.AuthService;
import com.kernel360.exception.BusinessException;
import com.kernel360.global.code.AcceptInterceptorErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AcceptInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        if (validateTargetUri(request)) {
            return true;
        }

        boolean result = true;
        String requestToken = request.getHeader("Authorization");

        if (!StringUtils.hasLength(requestToken)) { throw new BusinessException(AcceptInterceptorErrorCode.DOSE_NOT_EXIST_REQUEST_TOKEN); }

        if (!authService.validRequestToken(requestToken)) { throw new BusinessException(AcceptInterceptorErrorCode.FAILED_VALID_REQUEST_TOKEN); }

        //** 로그인한 IP와 다른 IP로 JWT 갱신을 시도하는 경우 **//
        if (!authService.verifyClientIP(request)) {
            throw new BusinessException(AcceptInterceptorErrorCode.FAILED_VERIFY_REQUEST_CLIENT_IP);
        }

        return result;
    }

    private boolean validateTargetUri(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        if (Objects.isNull(requestURI)) {
            return false;
        }

        if (!requestURI.startsWith("/reviews")) {
            return false;
        }

        if (HttpMethod.GET.matches(method)) {
            return true;
        }

        return false;
    }
}
