package tdc.edu.vn.project_mobile_be.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.dtos.requests.user.PasswordRequestDTO;
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
    private final ObjectMapper objectMapper;

    // Get All Users
    @GetMapping("/users")
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
    @PutMapping(value = "/users/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<?>> updateUser(
            @PathVariable("userId") UUID userId,
            @Valid @RequestPart(value = "request", required = true) String requestJson,
            @RequestPart(value = "image", required = false) MultipartFile userImagePath
    ) throws JsonProcessingException {
        UpdateUserRequestDTO request = objectMapper.readValue(requestJson, UpdateUserRequestDTO.class);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userService.updateUser(user, request, userImagePath);

        ResponseData<?> responseData = new ResponseData<>(
                HttpStatus.OK,
                "Thông tin người dùng đã được cập nhật thành công",
                user
        );
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
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
    @PutMapping(value = "/customer/myInfo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<?>> updateMyInfo(
            @RequestPart(value = "image", required = false) MultipartFile userImagePath,
            @Valid @RequestPart(value = "request", required = true) String requestJson
    ) throws JsonProcessingException {
        UpdateCustomerRequestDTO request = objectMapper.readValue(requestJson, UpdateCustomerRequestDTO.class);

        User user = userService.updateMyInfo(request, userImagePath);

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
    @GetMapping("/users/recent")
    public ResponseEntity<ResponseData<List<UserResponseDTO>>> getAllUserNew() {
        List<UserResponseDTO> users = userService.getAllUserNew();
        ResponseData<List<UserResponseDTO>> responseData = new ResponseData<>(
                HttpStatus.OK,
                "Lấy danh sách người dùng mới tạo gần nhất thành công",
                users
        );
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    @PutMapping("/myInfo/change-password")
    public ResponseEntity<ResponseData<String>> changePassword(@RequestBody @Valid PasswordRequestDTO request) {
        userService.changePassword(request);
        ResponseData<String> responseData = new ResponseData<>(
                HttpStatus.OK,
                "Đổi mật khẩu thành công",
                null
        );
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
