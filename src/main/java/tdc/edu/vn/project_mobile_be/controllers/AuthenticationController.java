package tdc.edu.vn.project_mobile_be.controllers;

import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdc.edu.vn.project_mobile_be.commond.ApiResponse;
import tdc.edu.vn.project_mobile_be.dtos.requests.jwt.AuthenticationRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.jwt.IntrospectRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.jwt.LogoutRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.jwt.RefreshRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.jwt.AuthenticationResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.jwt.IntrospectResponseDTO;
import tdc.edu.vn.project_mobile_be.services.impl.AuthenticationServiceImp;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1")
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationServiceImp authenticationServiceImp;

    @PostMapping("/auth/login")
    ApiResponse<AuthenticationResponseDTO> authentication(@RequestBody AuthenticationRequestDTO request) {
        var result = authenticationServiceImp.authenticate(request);
        return ApiResponse.<AuthenticationResponseDTO>builder()
                .result(result)
                .build();
    }
    @PostMapping("/auth/introspect")
    ApiResponse<IntrospectResponseDTO> introspect(@RequestBody IntrospectRequestDTO request) throws ParseException, JOSEException {
        var result = authenticationServiceImp.introspect(request);
        return ApiResponse.<IntrospectResponseDTO>builder()
                .result(result)
                .build();
    }
    @PostMapping("/auth/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequestDTO request) throws ParseException, JOSEException {
        authenticationServiceImp.logout(request);
        return ApiResponse.<Void>builder().build();
    }
    @PostMapping("/auth/refresh")
    ApiResponse<AuthenticationResponseDTO> refresh(@RequestBody RefreshRequestDTO request)
            throws ParseException, JOSEException{
        var result = authenticationServiceImp.refreshToken(request);
        return ApiResponse.<AuthenticationResponseDTO>builder()
                .result(result)
                .build();
    }
}
