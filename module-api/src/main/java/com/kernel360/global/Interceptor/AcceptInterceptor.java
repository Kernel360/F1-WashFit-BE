package com.kernel360.global.Interceptor;

import com.kernel360.auth.entity.Auth;
import com.kernel360.member.service.MemberService;
import com.kernel360.utils.ConvertSHA256;
import com.kernel360.utils.JWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class AcceptInterceptor implements HandlerInterceptor {

    private final JWT jwt;
    private final MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String requestToken = request.getHeader("Authorization");

        //토큰검증
        Boolean validateToken = jwt.validateToken(requestToken);

        //검증 된 토큰이라면 시간차를 구함
        if(validateToken) {
            long diffInMinutes = jwt.checkedTime(requestToken);
            //시간차가 2분 이내라면 해싱값을 비교함.
            if(diffInMinutes <= 2){
                String encryptToken = ConvertSHA256.convertToSHA256(requestToken);
                Auth storedAuthInfo = memberService.findOneAuthByJwt(encryptToken);

                //해싱값 일치시 재발급
                if(encryptToken.equals(storedAuthInfo)){
                    memberService.modifyAuthJwt(storedAuthInfo, jwt.ownerId(requestToken));
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // 컨트롤러가 실행된 후에 수행되는 로직
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // 뷰 렌더링이 완료된 후에 수행되는 로직
    }
}
