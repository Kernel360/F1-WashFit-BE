package com.kernel360.global.Interceptor;

import com.kernel360.auth.service.AuthService;
import com.kernel360.exception.BusinessException;
import com.kernel360.global.code.AcceptInterceptorErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AcceptInterceptor implements HandlerInterceptor {

    private final AuthService authService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        boolean result = true;
        String requestToken = request.getHeader("Authorization");

        if (requestToken.length() == 0) { throw new BusinessException(AcceptInterceptorErrorCode.DOSE_NOT_EXIST_REQUEST_TOKEN); }

        if (!authService.validRequestToken(requestToken)) { throw new BusinessException(AcceptInterceptorErrorCode.FAILED_VALID_REQUEST_TOKEN); }

        //IP수집 후 테이블에 저장

        return result;
    }

}
