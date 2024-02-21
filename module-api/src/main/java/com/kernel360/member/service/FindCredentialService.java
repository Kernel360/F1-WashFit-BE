package com.kernel360.member.service;

import com.kernel360.exception.BusinessException;
import com.kernel360.member.code.MemberErrorCode;
import com.kernel360.member.dto.MemberCredentialDto;
import com.kernel360.member.dto.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class FindCredentialService implements RedisUtils {

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

        String resetHyperLink = "<a href=\"" + resetUri
                + "\" style=\"display:inline-block;background:#fc1c49;border-radius:4px;font-weight:700;font-size:16px;line-height:1.5;"
                + "text-align:center;color:#ffffff;text-decoration:none;padding:12px 16px;box-sizing:border-box;height:48px\""
                + " target=\"_blank\">패스워드 재설정하기</a>";

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
                        "          <p style=\"padding-top:48px;font-weight:700;font-size:20px;line-height:1.5;color:#222\">\n"
                        +
                        "            Wash-Fit 계정의 비밀번호를 변경합니다.\n" +
                        "          </p>\n" +
                        "          <p style=\"font-size:16px;font-weight:400;line-height:1.5;padding-top:16px\">\n" +
                        "            아래의 링크를 통해 패스워드를 재설정해주세요.\n" +
                        "          </p>\n" +
                        "          <div style=\"padding-top:48px\">\n" + resetHyperLink +
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

        emailService.sendMail(dto.email(), "[No-Reply] Wash-Fit 아이디/비밀번호 찾기", htmlContent);
    }

    public String generatePasswordResetUri(HttpServletRequest request, MemberDto memberDto) {
        String resetToken = generateUUID();

        String uriString = UriComponentsBuilder.newInstance()
                                               .scheme("http")
                                               .host(request.getHeader("host"))
                                               .path("/member/reset-password")
                                               .queryParam("token", resetToken)
                                               .build()
                                               .toUriString();
        setExpiringData(resetToken, memberDto.id(), 5); // duration 값 상수로 변경관리 필요

        return uriString;
    }

    public String resetPassword(MemberCredentialDto credentialDto) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String value = valueOperations.get(credentialDto.authToken());

        if (value == null) {
            throw new BusinessException(MemberErrorCode.EXPIRED_PASSWORD_RESET_TOKEN);
        }

        memberService.resetPasswordByMemberId(value, credentialDto.password());

        return credentialDto.authToken();
    }

    private String generateUUID() {

        return UUID.randomUUID().toString();
    }

    @Override
    public String getData(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key); // TODO :: refactor
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
    public void expireData(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.getAndDelete(key);
    }
}
