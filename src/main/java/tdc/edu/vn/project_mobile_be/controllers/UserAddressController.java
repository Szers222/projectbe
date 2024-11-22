package tdc.edu.vn.project_mobile_be.controllers;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ApiResponse;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.dtos.requests.CreateAddressRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.UpdateAddressRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.jwt.RoleRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.RoleResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.entities.user.UserAddress;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.UserAddressRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.UserRepository;
import tdc.edu.vn.project_mobile_be.services.impl.UserAddressServiceImp;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAddressController {
    UserAddressServiceImp userAddressServiceImp;
    UserAddressRepository userAddressRepository;

    @PostMapping("/auth/user-address")
    public ResponseEntity<?> createAddress(@RequestBody CreateAddressRequestDTO request) {
        UserAddress userAddress = userAddressServiceImp.created(request);
        return ResponseEntity.ok(userAddress);
    }
    @PutMapping("/auth/user-address/{addressId}")
    public ResponseEntity<?> updateAddress(@PathVariable UUID addressId, @RequestBody UpdateAddressRequestDTO request) {
        UserAddress userAddress = userAddressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userAddressServiceImp.update(userAddress,request);

        ResponseData<?> responseData = new ResponseData<>(
                HttpStatus.CREATED
                , "Cap nhat thành công!"
                , userAddress);
        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

}
