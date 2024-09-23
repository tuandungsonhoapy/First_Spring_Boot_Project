package com.TDDev.Spring.Boot.Project.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> introspect(@RequestBody AuthenticationRequest request){
        return ApiResponse.<AuthenticationResponse>builder()
                .message("Login successful!")
                .result(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .message("Introspect successful!")
                .result(authenticationService.introspect(request))
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .message("Logout successful!")
                .build();
    }

    @PostMapping("/refresh-token")
    ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) throws ParseException, JOSEException {
        return ApiResponse.<AuthenticationResponse>builder()
                .message("Refresh token successful!")
                .result(authenticationService.refreshToken(request))
                .build();
    }
}
