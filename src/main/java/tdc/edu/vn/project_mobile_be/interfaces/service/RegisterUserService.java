package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.dtos.requests.RegisterRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.RegisterResponseDTO;

public interface RegisterUserService {
    RegisterResponseDTO register(RegisterRequestDTO request);
    void verify(String email, String otp);
}
