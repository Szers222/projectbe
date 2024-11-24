package tdc.edu.vn.project_mobile_be.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.dtos.requests.user.UpdateCustomerRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.user.UpdateUserRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.user.UserResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.UserRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class UserController {

    @Autowired
    @Qualifier("user_ServiceImpl")
    UserService userService;
    @Autowired
    UserRepository userRepository;
    // Get All Users
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseData<List<UserResponseDTO>>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        ResponseData<List<UserResponseDTO>> responseData =
                new ResponseData<>(
                        HttpStatus.OK,
                        "Lấy tất cả thành công",
                        users
                );
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    @PutMapping("/users/{users}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseData<?>> updateUser(@PathVariable("users") UUID userId, @RequestBody UpdateUserRequestDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userService.updateUser(user,request);

        ResponseData<?> responseData = new ResponseData<>(
                HttpStatus.CREATED
                , "User tạo thành công!"
                , user);
        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }
    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseData<UserResponseDTO>> getUser(@PathVariable UUID id) {
        UserResponseDTO user = userService.getUserById(id);
        ResponseData<UserResponseDTO> responseData = new ResponseData<>(
                HttpStatus.OK,
                "Lay theo ID USER",
                user
        );
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    @GetMapping("/users/myInfo")
    public ResponseEntity<ResponseData<UserResponseDTO>> getMyUsers() {
        UserResponseDTO userResponse = userService.getMyInfo();
        ResponseData<UserResponseDTO> responseData = new ResponseData<>(
                HttpStatus.OK,
                "Thông tin người dùng hiện tại",
                userResponse
        );
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    @PutMapping("/customer/myInfo")
    public ResponseEntity<ResponseData<?>> updateMyInfo(@RequestBody UpdateCustomerRequestDTO request) {
        User user = userService.updateMyInfo(request);
        ResponseData<?> responseData = new ResponseData<>(
                HttpStatus.OK,
                "Thông tin người dùng đã được cập nhật thành công",
                user
        );
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ResponseData<?>> deleteUser(@PathVariable UUID id) {
        userService.deleteUserById(id);

        ResponseData<?> responseData = new ResponseData<>(
                HttpStatus.OK, "User đã được xóa thành công", null
        );
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
