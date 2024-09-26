package com.TDDev.Spring.Boot.Project.controller;

import java.text.ParseException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.TDDev.Spring.Boot.Project.dto.request.Authentication.AuthenticationRequest;
import com.TDDev.Spring.Boot.Project.dto.request.RefreshTokenRequest.RefreshTokenRequest;
import com.TDDev.Spring.Boot.Project.dto.response.ApiResponse;
import com.TDDev.Spring.Boot.Project.dto.response.AuthenticationResponse;
import com.TDDev.Spring.Boot.Project.dto.response.IntrospectResponse;
import com.TDDev.Spring.Boot.Project.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request, HttpServletResponse response) {
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
    ApiResponse<Void> logout(HttpServletResponse response) throws ParseException, JOSEException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        // Lấy JWT từ Authentication
        Jwt jwt = (Jwt) authentication.getCredentials();

        // Token trong JWT
        String token = jwt.getTokenValue();

        authenticationService.logout(token);

        // Create a cookie with the same name as the token cookie and set its max age to 0
        Cookie cookie = new Cookie("identityToken", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);

        // Add the cookie to the response to delete it
        response.addCookie(cookie);

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
