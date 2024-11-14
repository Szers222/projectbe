package tdc.edu.vn.project_mobile_be.controllers;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdc.edu.vn.project_mobile_be.commond.ApiResponse;
import tdc.edu.vn.project_mobile_be.dtos.requests.CreateAddressRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.jwt.RoleRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.RoleResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.user.UserAddress;
import tdc.edu.vn.project_mobile_be.services.impl.UserAddressServiceImp;

@RestController
@RequestMapping("/api/v1")
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAddressController {
    UserAddressServiceImp userAddressServiceImp;

    @PostMapping("/auth/user-address")
    public ResponseEntity<?> createAddress(@RequestBody CreateAddressRequestDTO request) {
        UserAddress userAddress = userAddressServiceImp.created(request);
        return ResponseEntity.ok(userAddress);
    }

}
