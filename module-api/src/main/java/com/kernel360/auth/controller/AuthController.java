package com.kernel360.auth.controller;

import com.kernel360.auth.dto.AuthDto;
import com.kernel360.auth.service.AuthService;
import com.kernel360.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.kernel360.auth.code.AuthBusinessCode.SUCCESS_REQUEST_REGENERATED_JWT;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/reissuanceJWT")
    public ResponseEntity<ApiResponse<AuthDto>> reissuanceJWT(HttpServletRequest request){

        return ApiResponse.toResponseEntity(SUCCESS_REQUEST_REGENERATED_JWT,
                AuthDto.of(authService.generateTokenAndSaveAuth(request.getHeader("Authorization"))));
    }
}
