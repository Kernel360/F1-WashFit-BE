package com.kernel360.global.Interceptor;

import com.kernel360.auth.entity.Auth;
import com.kernel360.exception.BusinessException;
import com.kernel360.global.code.AcceptInterceptorErrorCode;
import com.kernel360.member.service.MemberService;
import com.kernel360.utils.ConvertSHA256;
import com.kernel360.utils.JWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AcceptInterceptor implements HandlerInterceptor {

    private final JWT jwt;
    private final MemberService memberService;
    private static final int CLOSING_PERIOD = 2;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        boolean result = true;
        String requestToken = request.getHeader("Authorization");

        if (requestToken == null || requestToken.isEmpty()) { throw new BusinessException(AcceptInterceptorErrorCode.DOSE_NOT_EXIST_REQUEST_TOKEN); }

        if (!validRequestToken(requestToken)) { throw new BusinessException(AcceptInterceptorErrorCode.FAILED_VALID_REQUEST_TOKEN); }

        long validRequestPeriod = validRequestPeriod(requestToken);

        if ( (validRequestPeriod <= CLOSING_PERIOD) ) {
            String encryptToken = ConvertSHA256.convertToSHA256(requestToken);
            Auth storedAuthInfo = getOneAuthByJwt(encryptToken);
            String newToken = reGeneratedToken(requestToken, storedAuthInfo);
            response.setHeader("Authorization", newToken);
        }

        return result;
    }
    private Auth getOneAuthByJwt(String encryptToken) {

        Auth result = memberService.findOneAuthByJwt(encryptToken);

        if(result == null) { throw new BusinessException(AcceptInterceptorErrorCode.FAILED_VALID_REQUEST_TOKEN_HASH); }
        if(!encryptToken.equals(result.getJwtToken())) { throw new BusinessException(AcceptInterceptorErrorCode.FAILED_VALID_REQUEST_TOKEN_HASH); }

        return result;
    }

    /**
     * 토큰 자체의 유효성 검사 (서버 시크릿 키)
     **/
    private boolean validRequestToken(String requestToken) { return jwt.validateToken(requestToken); }

    /**
     * 토큰의 유효시간이 현재시간과 비교하여 2분 이하인지
     **/
    private long validRequestPeriod(String requestToken) { return jwt.checkedTime(requestToken); }

    /**
     * 신규토큰 발급 후 저장
     **/
    private String reGeneratedToken(String requestToken, Auth storedAuthInfo) {
        String newToken = jwt.generateToken(jwt.ownerId(requestToken));
        String newEncryptToken = ConvertSHA256.convertToSHA256(newToken);

        storedAuthInfo = memberService.modifyAuthJwt(storedAuthInfo, newEncryptToken);
        memberService.reissuanceJwt(storedAuthInfo);

        return newToken;
    }

}
