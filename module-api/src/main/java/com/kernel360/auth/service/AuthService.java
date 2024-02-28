package com.kernel360.auth.service;

import com.kernel360.auth.entity.Auth;
import com.kernel360.auth.repository.AuthRepository;
import com.kernel360.utils.ConvertSHA256;
import com.kernel360.utils.JWT;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JWT jwt;
    private final AuthRepository authRepository;

    public Auth findOneByMemberNo(Long memberNo) {

        return authRepository.findOneByMemberNo(memberNo);
    }

    public Auth findOneByJwt(String encryptToken) {

        return authRepository.findOneByJwtToken(encryptToken);
    }

    public boolean validRequestToken(String requestToken) {
        boolean result = jwt.validateToken(requestToken);

        if(Objects.isNull(findOneByJwt(ConvertSHA256.convertToSHA256(requestToken)))) { result = false; }

        return result;
    }

    public String generateTokenAndSaveAuth(HttpServletRequest request) {
        String newToken = jwt.generateToken(JWT.ownerId(request.getHeader("Authorization")));
        Auth storedAuth = authRepository.findOneByJwtToken(ConvertSHA256.convertToSHA256(request.getHeader("Authorization")));
        String clientIP = getClientIP(request);

        modifyAuthJwt(storedAuth, ConvertSHA256.convertToSHA256(newToken), clientIP);

        authRepository.save(storedAuth);

        return newToken;
    }

    // TODO refactor :: auth 의 pk로 인해 조회 후 update - insert 를 결정하게 되어 줄일 필요가 있음. memberNo만으로 키를 잡는다면, flyway 또 수정해야함... 혹은 인증은 memorydb를 쓰는 방법 고려
    public void saveAuthByMember(Long memberNo, String encryptToken, HttpServletRequest request) {
        Auth authJwt = findOneByMemberNo(memberNo);
        String clientIP = getClientIP(request);

        //결과 없으면 entity 로 신규 생성
        authJwt = Optional.ofNullable(authJwt)
                          .map(modifyAuth -> modifyAuthJwt(modifyAuth, encryptToken, clientIP))
                          .orElseGet(() -> createAuthJwt(memberNo, encryptToken, clientIP));

        authRepository.save(authJwt);
    }

    public Auth createAuthJwt(Long memberNo, String encryptToken, String clientIP) {

        return Auth.of(null, memberNo, encryptToken, null, clientIP);
    }

    public Auth modifyAuthJwt(Auth modifyAuth, String encryptToken, String clientIP) {
        modifyAuth.updateJwt(encryptToken, clientIP);

        return modifyAuth;
    }

    /**
     * @param request HttpServletRequest
     * @return 로그인한 유저이고, 로그인한 IP 와 같으면 참을 반환, 그 이외에는 거짓을 반환
     */
    public boolean verifyClientIP(HttpServletRequest request) {
        String clientIP = getClientIP(request);
        if (!StringUtils.hasLength(request.getHeader("Authorization"))) { // 인증 토큰이 없는 경우(로그인 하지 않은 유저의 경우)
            return true;
        }

        // 인증 토큰이 있고 ip 주소가 같은 경우 true
        if (StringUtils.hasLength(request.getHeader("Authorization"))) {
            Auth auth = authRepository.findOneByJwtToken(
                    ConvertSHA256.convertToSHA256(request.getHeader("Authorization")));
            return auth.getClientIP().equals(clientIP);
        }
        // 그 이외 false
        return false;
    }

    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        log.info("> X-FORWARDED-FOR : " + ip);

        if (!StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            log.info("> Proxy-Client-IP : " + ip);
        }
        if (!StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.info(">  WL-Proxy-Client-IP : " + ip);
        }
        if (!StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            log.info("> HTTP_CLIENT_IP : " + ip);
        }
        if (!StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.info("> HTTP_X_FORWARDED_FOR : " + ip);
        }
        if (!StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            log.info("> getRemoteAddr : " + ip);
        }
        log.info("> Result : IP Address : " + ip);

        return ip;
    }
}
