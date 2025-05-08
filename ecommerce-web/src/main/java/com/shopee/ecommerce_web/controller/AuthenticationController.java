package com.shopee.ecommerce_web.controller;

import com.nimbusds.jose.JOSEException;
import com.shopee.ecommerce_web.dto.request.*;
import com.shopee.ecommerce_web.dto.response.AuthenticationResponse;
import com.shopee.ecommerce_web.dto.response.IntrospectResponse;
import com.shopee.ecommerce_web.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/outbound/authentication")
    ApiResponse<AuthenticationResponse> outboundAuthenticate(
            @RequestParam("code") String code,
            HttpServletResponse responseHTTP
    ){
        var result = authenticationService.outboundAuthenticate(code, responseHTTP);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response
    ) {
        var result = authenticationService.authenticate(request, response); // Truyền response vào service
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }


    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshRequest request, HttpServletResponse response)
            throws ParseException, JOSEException {
        // Gọi service để refresh token và truyền response vào để thêm cookie
        var result = authenticationService.refreshToken(request, response);

        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }


    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody LogoutRequest request, HttpServletResponse response)
            throws ParseException, JOSEException {
        authenticationService.logout(request, response); // Truyền response xuống service để xóa cookie
        return ApiResponse.<Void>builder()
                .message("Đăng xuất thành công")
                .build();
    }
}
