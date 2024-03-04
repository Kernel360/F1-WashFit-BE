package com.kernel360.member.service;

import com.kernel360.exception.BusinessException;
import com.kernel360.member.code.MemberErrorCode;
import com.kernel360.member.dto.MemberCredentialDto;
import com.kernel360.member.dto.MemberDto;
import java.net.URI;
import java.time.Duration;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class FindCredentialService implements RedisUtils {

    @Value("${constants.password-reset-token.duration-minute}")
    private int TOKEN_DURATION;

    @Value("${constants.fe-host-url}")
    private String FE_HOST_HTTP_URL;

    private final RedisTemplate<String, String> redisTemplate;

    private final MemberService memberService;

    private final SendEmailService emailService;

    public void sendMemberId(String email, String memberId) {
        String textContent = "가입하신 아이디는 " + "'" + memberId + "' 입니다.";
        String htmlContent =
                "<div style=\"margin:0;padding:0;font-size:14px;line-height:1.5;background-color:#fff;color:#2e2e2e;font-weight:400\">\n"
                        +
                        "  <table style=\"margin:40px auto 20px;text-align:left;border-collapse:collapse;border:0;width:600px;padding:64px 16px;box-sizing:border-box;display:block\">\n"
                        +
                        "    <tbody>\n" +
                        "      <tr>\n" +
                        "        <td><h1>Wash-Fit 아이디/패스워드 찾기</h1></td>\n" +
                        "      </tr>\n" +
                        "      <tr>\n" +
                        "        <td>\n" +
                        "          <div style=\"padding-top:48px\">\n" + textContent +
                        "          </div>\n" +
                        "        </td>\n" +
                        "      </tr>\n" +
                        "      <tr>\n" +
                        "        <td style=\"padding-top:48px\" colspan(\"2\")>\n" +
                        "          <p>기타 질문사항은 고객센터에 문의 주시길 바랍니다.</p>\n" +
                        "        </td>\n" +
                        "      </tr>\n" +
                        "    </tbody>\n" +
                        "  </table>\n" +
                        "</div>";

        emailService.sendMail(email, "[No-Reply] Wash-Fit 아이디/비밀번호 찾기", htmlContent);
    }

    public void sendPasswordResetUri(String resetUri, MemberDto dto) {

        String htmlContent = "<!DOCTYPE html>\n"
                + "<html lang=\"ko\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <title>Password Recovery</title>\n"
                + "</head>\n"
                + "<body style=\"font-family: Arial, sans-serif;\">\n"
                + "<div style=\"margin: 0 auto; max-width: 700px;\">\n"
                + "    <div style=\"text-align: center;\">\n"
                + "        <img src=\"/placeholder.svg\" alt=\"WashFit\" style=\"width: 100px; height: 100px; object-fit: cover;\">\n"
                + "        <h1 style=\"font-size: 1.5em; font-weight: bold;\">WashFit 계정의 비밀번호를 변경합니다.</h1>\n"
                + "        <p>아래의 링크를 통해 패스워드를 재설정하세요.</p>\n"
                + "        <a href=" + resetUri + "\n"
                + "           style=\"display: inline-block; padding: 6px 20px; text-decoration: none; border-radius: 5px; text-align: center; font-weight: bold; color: white; background-color: #0075FF;\">\n"
                + "            패스워드 재설정하기\n"
                + "        </a>\n"
                + "    </div>\n"
                + "    <br>\n"
                + "    <div style=\"border-top: 1px solid lightgrey; padding: 20px; text-align:center;\">\n"
                + "        <h2 style=\"font-weight:bold; font-size: 1.2em;\">고객센터 운영시간</h2>\n"
                + "        <p>평일 10:00~19:00 (주말 및 공휴일 제외/ 점심시간 13:00~14:00)</p>\n"
                + "        <a href=\"mailto:washfit240126@gmail.com\"\n"
                + "           style=\"display: inline-block; padding: 6px 20px; text-decoration: none; text-align: center; font-weight: bold; color: black; background-color: lightgrey; width: 50%;\">"
                + "고객센터 문의하기</a>\n"
                + "    </div>\n"
                + "    <div style=\"border-top: 1px solid lightgrey; padding: 20px; text-align:center;\">\n"
                + "        <p style=\"font-weight:bold;\">FAST CAMPUS</p>\n"
                + "        <p>(주)데이원캠퍼니</p>\n"
                + "        <p>서울특별시 강남구 테헤란로 231, 센트럴프라자 WEST 6층, 7층</p>\n"
                + "    </div>\n"
                + "</div>\n"
                + "</body>\n"
                + "</html>";

        emailService.sendMail(dto.email(), "[No-Reply] Wash-Fit 아이디/비밀번호 찾기", htmlContent);
    }

    public String generatePasswordResetPageUri(MemberDto memberDto) {
        String accessToken = generateUUID();

        String uriString = UriComponentsBuilder.fromHttpUrl(FE_HOST_HTTP_URL)
                                               .path("/change-password")
                                               .queryParam("token", accessToken)
                                               .build()
                                               .toUriString();
        setExpiringData(accessToken, memberDto.id(), TOKEN_DURATION);

        return uriString;
    }

    public String resetPassword(MemberCredentialDto credentialDto) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String memberId = valueOperations.get(credentialDto.authToken());

        if (Objects.isNull(memberId)) {
            throw new BusinessException(MemberErrorCode.EXPIRED_TOKEN);
        }
        memberService.resetPasswordByMemberId(memberId, credentialDto.password());

        return credentialDto.authToken();
    }

    private String generateUUID() {

        return UUID.randomUUID().toString();
    }

    @Override
    public String getData(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        if (Objects.isNull(valueOperations.get(key))) {
            throw new BusinessException(MemberErrorCode.EXPIRED_TOKEN);
        }

        return valueOperations.get(key);
    }

    @Override
    public void setData(String key, String value) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    @Override
    public void setExpiringData(String key, String value, int duration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofMinutes(duration);
        valueOperations.set(key, value, expireDuration);
    }

    @Override
    public void getAndExpireData(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        if (Objects.isNull(valueOperations.get(key))) {
            throw new BusinessException(MemberErrorCode.EXPIRED_TOKEN);
        }

        valueOperations.getAndDelete(key);
    }

}
