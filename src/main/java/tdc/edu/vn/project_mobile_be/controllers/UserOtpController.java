package tdc.edu.vn.project_mobile_be.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.dtos.requests.EmailRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.RegisterRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.ResetPasswordRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.interfaces.service.UserOtpService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserOtpController {
    @Autowired
    private final UserOtpService userOtpService;

    @PostMapping("/create-email")
    public ResponseEntity<ResponseData<?>> createEmail(@RequestBody @Valid EmailRequestDTO emailRequest) {
        User user = userOtpService.createEmail(emailRequest);
        ResponseData<?> responseData = new ResponseData<>(
                HttpStatus.CREATED
                , "Vui long xac nhan OTP !"
                , user);
        return new ResponseEntity<ResponseData<?>>(responseData, HttpStatus.CREATED);
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam String email, @RequestParam String otp){
        try {
            userOtpService.verify(email, otp);
            return new ResponseEntity<>("User verified successfully",HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<ResponseData<?>> registerUser(
            @RequestParam("userEmail") String userEmail,
            @RequestBody @Valid RegisterRequestDTO registerRequest) {
        User user = userOtpService.register(userEmail, registerRequest);
        ResponseData<?> responseData = new ResponseData<>(
                HttpStatus.CREATED
                , " Đăng ký thanh công !"
                , user);
        return ResponseEntity.ok(responseData);
    }
    @PostMapping("/forgot")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        try {
            userOtpService.forgotPassword(email);
            return new ResponseEntity<>("Xac thuc email",HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/reset")
    public ResponseEntity<ResponseData<?>> resetPassword(
            @RequestParam("email") String email,
            @RequestParam("otp") String otp,
            @RequestBody @Valid ResetPasswordRequestDTO resetPasswordRequest){
        User user = userOtpService.resetPassword(email,otp,resetPasswordRequest);
        ResponseData<?> responseData = new ResponseData<>(
                HttpStatus.CREATED
                ,"Tao password moi thanh cong"
                ,user);
        return ResponseEntity.ok(responseData);
    }


}
