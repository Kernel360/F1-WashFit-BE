package com.kernel360.config;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
@Configuration
public class AdminAuditConfig implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String createId = Optional.ofNullable(request.getParameter("id")).orElse("module-admin");

        return Optional.of(createId);
    }
}
