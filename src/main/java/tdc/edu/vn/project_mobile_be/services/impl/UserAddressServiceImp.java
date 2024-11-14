package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.dtos.requests.CreateAddressRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.entities.user.UserAddress;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.UserAddressRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.UserRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.UserAddressService;
@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAddressServiceImp {

    UserAddressRepository userAddressRepository;
    UserAddressService userAddressService;
    UserRepository userRepository; // Add this

    public UserAddress created(CreateAddressRequestDTO request) {
        // Find User by ID (make sure to handle the case where the user doesn't exist)
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create UserAddress and set properties
        UserAddress userAddress = UserAddress.builder()
                .addressName(request.getUserAddress())
                .ward(request.getWard())
                .district(request.getDistrict())
                .city(request.getCity())
                .user(user) // Set the user
                .build();

        // Save UserAddress to database
        return userAddressRepository.save(userAddress);
    }
}

