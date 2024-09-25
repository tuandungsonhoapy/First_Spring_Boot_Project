package com.TDDev.Spring.Boot.Project.controller;

import java.text.ParseException;
import java.util.Arrays;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.TDDev.Spring.Boot.Project.dto.request.Authentication.AuthenticationRequest;
import com.TDDev.Spring.Boot.Project.dto.request.Authentication.IntrospectRequest;
import com.TDDev.Spring.Boot.Project.dto.request.LogoutRequest.LogoutRequest;
import com.TDDev.Spring.Boot.Project.dto.request.RefreshTokenRequest.RefreshTokenRequest;
import com.TDDev.Spring.Boot.Project.dto.response.ApiResponse;
import com.TDDev.Spring.Boot.Project.dto.response.AuthenticationResponse;
import com.TDDev.Spring.Boot.Project.dto.response.IntrospectResponse;
import com.TDDev.Spring.Boot.Project.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        var result = authenticationService.authenticate(request);

        Cookie cookie = new Cookie("identityToken", result.getToken());
        cookie.setMaxAge(12 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return ApiResponse.<AuthenticationResponse>builder()
                .message("Login successful!")
                .result(result)
                .build();
    }

    @GetMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(HttpServletRequest httpServletRequest)
            throws ParseException, JOSEException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        // Lấy JWT từ Authentication
        Jwt jwt = (Jwt) authentication.getCredentials();

        // Token trong JWT
        String token = jwt.getTokenValue();

        return ApiResponse.<IntrospectResponse>builder()
                .message("Introspect successful!")
                .result(authenticationService.introspect(token))
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().message("Logout successful!").build();
    }

    @PostMapping("/refresh-token")
    ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request)
            throws ParseException, JOSEException {
        return ApiResponse.<AuthenticationResponse>builder()
                .message("Refresh token successful!")
                .result(authenticationService.refreshToken(request))
                .build();
    }
}
