package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.dtos.requests.user.EmailRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.RegisterRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.ResetPasswordRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.user.User;

public interface UserOtpService {
    User register(String userEmail,RegisterRequestDTO request);
    void verify(String email, String otp);
    User createEmail(EmailRequestDTO request);
    void forgotPassword(String email);
    void verifyOtp(String email, String otp);
    User resetPassword(String email, ResetPasswordRequestDTO request);
}
