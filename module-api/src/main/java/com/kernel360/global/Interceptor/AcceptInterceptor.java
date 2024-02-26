package com.kernel360.global.Interceptor;

import com.kernel360.auth.service.AuthService;
import com.kernel360.exception.BusinessException;
import com.kernel360.global.code.AcceptInterceptorErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AcceptInterceptor implements HandlerInterceptor {

    private final AuthService authService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        boolean result = true;
        String requestToken = request.getHeader("Authorization");

        if (StringUtils.hasLength(requestToken)) { throw new BusinessException(AcceptInterceptorErrorCode.DOSE_NOT_EXIST_REQUEST_TOKEN); }

        if (!authService.validRequestToken(requestToken)) { throw new BusinessException(AcceptInterceptorErrorCode.FAILED_VALID_REQUEST_TOKEN); }

        // TODO:: IP 수집 후 테이블에 저장 -> 토큰 갱신 요청시 확인해서 토큰 재발급

        return result;
    }

}
