package tdc.edu.vn.project_mobile_be.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.dtos.requests.RegisterRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.RegisterResponseDTO;
import tdc.edu.vn.project_mobile_be.interfaces.service.RegisterUserService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RegisterController {
    @Autowired
    private final RegisterUserService registerUserService;

    @PostMapping("/register")
    public ResponseEntity<ResponseData<?>> register(@RequestBody RegisterRequestDTO request) {
        RegisterResponseDTO response = registerUserService.register(request);
        ResponseData<?> responseData = new ResponseData<>(
                HttpStatus.CREATED
                , "Category tạo thành công !"
                , response);
        return new ResponseEntity<ResponseData<?>>(responseData, HttpStatus.CREATED);
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam String email, @RequestParam String otp){
        try {
            registerUserService.verify(email, otp);
            return new ResponseEntity<>("User verified successfully",HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


}
