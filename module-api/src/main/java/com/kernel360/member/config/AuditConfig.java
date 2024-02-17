package com.kernel360.member.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

//
@Configuration
public class AuditConfig implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String createId = Optional.ofNullable(request.getParameter("id")).orElse("admin");

        return Optional.of(createId);
    }
}
