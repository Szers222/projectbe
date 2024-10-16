package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.dtos.requests.CreateUserRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO createUser(CreateUserRequestDTO createUserRequestDTO);
    List<UserResponseDTO> getAllUsers();
}