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
import tdc.edu.vn.project_mobile_be.dtos.requests.CreateUserRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.UpdateUserRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.UserResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.user.User;
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

    @PostMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseData<?>> createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {

        User createdUser = userService.createUser(createUserRequestDTO);
        ResponseData<?> responseData = new ResponseData<>(
                HttpStatus.CREATED
                , "User tạo thành công!"
                , createdUser);
        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }
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
    @PutMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseData<?>> updateUser(@PathVariable UUID userId, @RequestBody UpdateUserRequestDTO request) {
        User user = userService.updateUser(userId,request);

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
    @PreAuthorize("hasAuthority('PERMISSION_GETID')")
    public ResponseEntity<ResponseData<UserResponseDTO>> getMyUsers() {
        UserResponseDTO userResponse = userService.getMyInfo();
        ResponseData<UserResponseDTO> responseData = new ResponseData<>(
                HttpStatus.OK,
                "Thông tin người dùng hiện tại",
                userResponse
        );
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
