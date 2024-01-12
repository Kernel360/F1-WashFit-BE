package com.kernel360.global.filter;

import com.kernel360.global.Interceptor.AcceptInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
    private final AcceptInterceptor acceptInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(acceptInterceptor)
                .addPathPatterns("/member");
                //.excludePathPatterns("/public/**"); // 제외할 URL 패턴
    }

}
