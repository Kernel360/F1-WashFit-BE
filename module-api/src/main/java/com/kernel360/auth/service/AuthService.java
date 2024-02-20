package com.kernel360.auth.service;

import com.kernel360.auth.entity.Auth;
import com.kernel360.auth.repository.AuthRepository;
import com.kernel360.utils.ConvertSHA256;
import com.kernel360.utils.JWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

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

    public String generateTokenAndSaveAuth(String requestToken) {
        String newToken = jwt.generateToken(JWT.ownerId(requestToken));
        Auth storedAuth = authRepository.findOneByJwtToken(ConvertSHA256.convertToSHA256(requestToken));

        modifyAuthJwt(storedAuth, ConvertSHA256.convertToSHA256(newToken));

        authRepository.save(storedAuth);

        return newToken;
    }

    // TODO refactor :: auth의 pk로 인해 조회 후 update - insert 를 결정하게 되어 줄일 필요가 있음. memberNo만으로 키를 잡는다면, flyway 또 수정해야함... 혹은 인증은 memorydb를 쓰는 방법 고려
    public void saveAuthByMember(Long memberNo, String encryptToken) {
        Auth authJwt = findOneByMemberNo(memberNo);

        //결과 없으면 entity로 신규 생성
        authJwt = Optional.ofNullable(authJwt)
                          .map(modifyAuth -> modifyAuthJwt(modifyAuth, encryptToken))
                          .orElseGet(() -> createAuthJwt(memberNo, encryptToken));

        authRepository.save(authJwt);
    }

    public Auth createAuthJwt(Long memberNo, String encryptToken) {

        return Auth.of(null, memberNo, encryptToken, null);
    }

    public Auth modifyAuthJwt(Auth modifyAuth, String encryptToken) {
        modifyAuth.updateJwt(encryptToken);

        return modifyAuth;
    }

}
