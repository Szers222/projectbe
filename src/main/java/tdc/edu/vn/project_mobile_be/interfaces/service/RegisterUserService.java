package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.dtos.requests.EmailRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.RegisterRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.RegisterResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.user.User;

public interface RegisterUserService {
    User register(String userEmail,RegisterRequestDTO request);
    void verify(String email, String otp);
    User createEmail(EmailRequestDTO request);
}
