package tdc.edu.vn.project_mobile_be.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.commond.customexception.ValidateException;
import tdc.edu.vn.project_mobile_be.dtos.requests.CreateUserRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.UserResponseDTO;
import tdc.edu.vn.project_mobile_be.interfaces.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create a new user
//    @PostMapping
//    public ResponseEntity<ResponseData<UserResponseDTO>> createUser(
//            @RequestBody @Valid CreateUserRequestDTO createUserRequestDTO,
//            BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            Map<String, String> errors = new HashMap<>();
//            bindingResult.getFieldErrors().forEach(fieldError -> {
//                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
//            });
//            StringBuilder errorString = new StringBuilder();
//            errors.forEach((key, value) -> errorString.append(key).append(": ").append(value).append("; "));
//            throw new ValidateException(errorString.toString());
//        }
//        UserResponseDTO createdUser = userService.createUser(createUserRequestDTO);
//        ResponseData<UserResponseDTO> responseData =
//                new ResponseData<>(HttpStatus.CREATED, "Thêm mới thành công", createdUser);
//        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
//    }

    // Get All Users
    @GetMapping
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
}
