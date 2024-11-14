package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.dtos.requests.EmailRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.RegisterRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.ResetPasswordRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.user.User;

public interface UserOtpService {
    User register(String userEmail,RegisterRequestDTO request);
    void verify(String email, String otp);
    User createEmail(EmailRequestDTO request);
    void forgotPassword(String email);
    User resetPassword(String email,String otp,ResetPasswordRequestDTO request);
}
